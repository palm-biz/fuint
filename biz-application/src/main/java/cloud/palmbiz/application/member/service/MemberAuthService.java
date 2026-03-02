package cloud.palmbiz.application.member.service;

import com.alibaba.fastjson.JSONObject;
import cloud.palmbiz.common.enums.GenderEnum;
import cloud.palmbiz.common.enums.MemberSourceEnum;
import cloud.palmbiz.common.enums.PlatformTypeEnum;
import cloud.palmbiz.common.enums.SettingTypeEnum;
import cloud.palmbiz.common.enums.UserSettingEnum;
import cloud.palmbiz.common.enums.YesOrNoEnum;
import cloud.palmbiz.application.OpenGiftService;
import cloud.palmbiz.application.marketing.commission.service.CommissionRelationService;
import cloud.palmbiz.application.SettingService;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.common.util.PhoneFormatCheckUtils;
import cloud.palmbiz.common.utils.StringUtil;
import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.model.MemberGrade;
import cloud.palmbiz.domain.member.repository.MemberRepository;
import cloud.palmbiz.domain.member.repository.MemberGradeRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.mapper.MtUserMapper;
import cloud.palmbiz.infrastructure.model.MtSetting;
import cloud.palmbiz.infrastructure.model.MtUser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员认证服务
 * 处理会员的登录认证相关操作
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class MemberAuthService {

    private static final Logger logger = LoggerFactory.getLogger(MemberAuthService.class);

    private final MemberRepository memberRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final MemberCommandService memberCommandService;
    private final SettingService settingService;
    private final OpenGiftService openGiftService;
    private final CommissionRelationService commissionRelationService;
    private final MtUserMapper mtUserMapper;

    /**
     * 通过手机号登录（自动注册）
     */
    @Transactional(rollbackFor = Exception.class)
    public Member loginByMobile(Integer merchantId, String mobile, String shareId, String ip) throws BusinessCheckException {
        // 检查手机号是否存在
        Member member = memberRepository.findByMobile(merchantId, mobile);
        if (member != null) {
            return member;
        }

        // 自动注册
        String memberNo = CommonUtil.createUserNo();
        String nickName = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

        MemberGrade initGrade = memberGradeRepository.findInitGrade(merchantId);
        Integer gradeId = initGrade != null ? initGrade.getId() : null;

        member = Member.create(
                memberNo,
                nickName,
                mobile,
                gradeId,
                merchantId,
                0,
                MemberSourceEnum.MOBILE_LOGIN.getKey(),
                ip
        );

        memberRepository.save(member);

        // 开卡赠礼
        try {
            openGiftService.openGift(member.getMemberIdValue(), member.getGradeId(), true);
        } catch (Exception e) {
            logger.error("开卡赠礼失败: memberId={}, error={}", member.getMemberIdValue(), e.getMessage());
        }

        // 设置分佣关系
        try {
            MtUser mtUser = mtUserMapper.selectById(member.getMemberIdValue());
            commissionRelationService.setCommissionRelation(mtUser, shareId);
        } catch (Exception e) {
            logger.error("设置分佣关系失败: memberId={}, error={}", member.getMemberIdValue(), e.getMessage());
        }

        return member;
    }

    /**
     * 通过微信OpenID登录（自动注册）
     */
    @Transactional(rollbackFor = Exception.class)
    public Member loginByOpenId(Integer merchantId, String openId, JSONObject userInfo) throws BusinessCheckException {
        Member member = memberRepository.findByOpenId(merchantId, openId);

        // 会员已存在且状态正常
        if (member != null && member.isActive()) {
            return handleExistingMember(member, userInfo);
        }

        // 会员被禁用
        if (member != null && !member.isActive()) {
            return null;
        }

        // 解析用户信息
        String avatar = StringUtil.isNotEmpty(userInfo.getString("avatarUrl")) ? userInfo.getString("avatarUrl") : "";
        String gender = StringUtil.isNotEmpty(userInfo.getString("gender")) ? userInfo.getString("gender") : GenderEnum.MAN.getKey().toString();
        String country = StringUtil.isNotEmpty(userInfo.getString("country")) ? userInfo.getString("country") : "";
        String province = StringUtil.isNotEmpty(userInfo.getString("province")) ? userInfo.getString("province") : "";
        String city = StringUtil.isNotEmpty(userInfo.getString("city")) ? userInfo.getString("city") : "";
        String storeId = StringUtil.isNotEmpty(userInfo.getString("storeId")) ? userInfo.getString("storeId") : "0";
        String nickName = StringUtil.isNotEmpty(userInfo.getString("nickName")) ? userInfo.getString("nickName") : "";
        String mobile = StringUtil.isNotEmpty(userInfo.getString("phone")) ? userInfo.getString("phone") : "";
        String shareId = StringUtil.isNotEmpty(userInfo.getString("shareId")) ? userInfo.getString("shareId") : "0";
        String source = StringUtil.isNotEmpty(userInfo.getString("source")) ? userInfo.getString("source") : MemberSourceEnum.WECHAT_LOGIN.getKey();
        String platform = StringUtil.isNotEmpty(userInfo.getString("platform")) ? userInfo.getString("platform") : "";
        String ip = StringUtil.isNotEmpty(userInfo.getString("ip")) ? userInfo.getString("ip") : "";

        // 需要手机号登录
        if (StringUtil.isEmpty(mobile) && !platform.equals(PlatformTypeEnum.H5.getCode())) {
            MtSetting mtSetting = settingService.querySettingByName(merchantId, SettingTypeEnum.USER.getKey(), UserSettingEnum.LOGIN_NEED_PHONE.getKey());
            if (mtSetting != null && mtSetting.getValue().equals(YesOrNoEnum.TRUE.getKey())) {
                // 返回临时会员对象，提示需要绑定手机号
                Member tempMember = Member.create(
                        "temp",
                        "temp",
                        null,
                        0,
                        merchantId,
                        0,
                        source,
                        ip
                );
                tempMember.bindOpenId(openId);
                return tempMember;
            }
        }

        // 手机号已存在，绑定OpenID
        if (StringUtil.isNotEmpty(mobile)) {
            Member existingMember = memberRepository.findByMobile(merchantId, mobile);
            if (existingMember != null) {
                existingMember.bindOpenId(openId);
                memberRepository.save(existingMember);
                return existingMember;
            }
        }

        // 创建新会员
        return createMemberFromWechat(
                merchantId,
                openId,
                nickName,
                mobile,
                avatar,
                gender,
                country,
                province,
                city,
                storeId,
                source,
                ip,
                shareId
        );
    }

    /**
     * 验证密码登录
     */
    public Member loginByPassword(Integer merchantId, String accountName, String password) throws BusinessCheckException {
        Member member = null;

        // 尝试用手机号查询
        if (PhoneFormatCheckUtils.isChinaPhoneLegal(accountName)) {
            member = memberRepository.findByMobile(merchantId, accountName);
        }

        // 尝试用会员号查询
        if (member == null) {
            member = memberRepository.findByMemberNo(merchantId, accountName);
        }

        // 尝试用会员名称查询
        if (member == null) {
            member = memberRepository.findByName(merchantId, accountName);
        }

        if (member == null) {
            throw new BusinessCheckException("账号或密码错误");
        }

        // 验证密码
        if (!member.verifyPassword(password)) {
            throw new BusinessCheckException("账号或密码错误");
        }

        // 验证状态
        if (!member.isActive()) {
            throw new BusinessCheckException("账号已被禁用");
        }

        return member;
    }

    // ==================== 私有方法 ====================

    /**
     * 处理已存在的会员
     */
    private Member handleExistingMember(Member member, JSONObject userInfo) {
        String mobile = StringUtil.isNotEmpty(userInfo.getString("phone")) ? userInfo.getString("phone") : "";

        // 补充手机号
        if (StringUtil.isNotEmpty(mobile) && PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)) {
            member.updateMobile(mobile);
            memberRepository.save(member);
        }

        // 补充会员号
        if (member.getMemberNoValue() == null || member.getMemberNoValue().isEmpty()) {
            String memberNo = CommonUtil.createUserNo();
            // 需要更新会员号逻辑
            memberRepository.save(member);
        }

        return member;
    }

    /**
     * 从微信信息创建会员
     */
    private Member createMemberFromWechat(
            Integer merchantId,
            String openId,
            String nickName,
            String mobile,
            String avatar,
            String gender,
            String country,
            String province,
            String city,
            String storeId,
            String source,
            String ip,
            String shareId) throws BusinessCheckException {

        // 昵称为空，用手机号
        if (StringUtil.isEmpty(nickName) && StringUtil.isNotEmpty(mobile)) {
            nickName = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }

        // 生成会员号
        String userNo = CommonUtil.createUserNo();

        // 清理XSS
        mobile = CommonUtil.replaceXSS(mobile);
        avatar = CommonUtil.replaceXSS(avatar);
        nickName = CommonUtil.replaceXSS(nickName);

        // 获取初始等级
        MemberGrade initGrade = memberGradeRepository.findInitGrade(merchantId);
        Integer gradeId = initGrade != null ? initGrade.getId() : null;

        // 创建会员
        Member member = Member.create(
                userNo,
                StringUtil.isNotEmpty(nickName) ? nickName : userNo,
                mobile,
                gradeId,
                merchantId,
                StringUtil.isNotEmpty(storeId) ? Integer.parseInt(storeId) : 0,
                source,
                ip
        );

        // 绑定OpenID
        member.bindOpenId(openId);

        // 设置头像
        if (StringUtil.isNotEmpty(avatar)) {
            member.updateBasicInfo(null, avatar, null, null, null, null, null, null);
        }

        // 微信用户性别转换: 1:男；2:女 0:未知
        Integer sex = GenderEnum.MAN.getKey();
        if (gender.equals(GenderEnum.FEMALE.getKey().toString())) {
            sex = GenderEnum.UNKNOWN.getKey();
        } else if (gender.equals(GenderEnum.UNKNOWN.getKey().toString())) {
            sex = GenderEnum.FEMALE.getKey();
        }

        // 设置地址和性别
        String address = country + province + city;
        member.updateBasicInfo(null, null, sex, null, null, null, address, null);

        // 保存会员
        memberRepository.save(member);

        // 开卡赠礼
        try {
            openGiftService.openGift(member.getMemberIdValue(), member.getGradeId(), true);
        } catch (Exception e) {
            logger.error("开卡赠礼失败: memberId={}, error={}", member.getMemberIdValue(), e.getMessage());
        }

        // 设置分佣关系
        try {
            MtUser mtUser = mtUserMapper.selectById(member.getMemberIdValue());
            commissionRelationService.setCommissionRelation(mtUser, shareId);
        } catch (Exception e) {
            logger.error("设置分佣关系失败: memberId={}, error={}", member.getMemberIdValue(), e.getMessage());
        }

        return member;
    }
}
