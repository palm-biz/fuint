package cloud.palmbiz.application.member.service;

import cloud.palmbiz.application.member.command.*;
import cloud.palmbiz.common.service.OpenGiftService;
import cloud.palmbiz.common.service.CommissionRelationService;
import cloud.palmbiz.common.service.SendSmsService;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.common.util.SeqUtil;
import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.model.MemberGrade;
import cloud.palmbiz.domain.member.model.MemberId;
import cloud.palmbiz.domain.member.repository.MemberGradeRepository;
import cloud.palmbiz.domain.member.repository.MemberRepository;
import cloud.palmbiz.domain.member.service.MemberDomainService;
import cloud.palmbiz.framework.annoation.OperationServiceLog;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.mapper.MtUserMapper;
import cloud.palmbiz.infrastructure.model.MtUser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员命令服务
 * 处理会员的创建、更新、删除等命令操作
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class MemberCommandService {

    private static final Logger logger = LoggerFactory.getLogger(MemberCommandService.class);

    private final MemberRepository memberRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final MemberDomainService memberDomainService;
    private final OpenGiftService openGiftService;
    private final CommissionRelationService commissionRelationService;
    private final SendSmsService sendSmsService;
    private final MtUserMapper mtUserMapper;

    /**
     * 注册会员
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "注册会员")
    public MtUser registerMember(RegisterMemberCommand command) throws BusinessCheckException {
        // 检查手机号是否已存在
        if (command.getMobile() != null && !command.getMobile().isEmpty()) {
            if (memberDomainService.isMobileExists(command.getMerchantId(), command.getMobile())) {
                Member existingMember = memberRepository.findByMobile(command.getMerchantId(), command.getMobile());
                return mtUserMapper.selectById(existingMember.getMemberIdValue());
            }
        }

        // 生成会员号
        String memberNo = command.getMemberNo();
        if (memberNo == null || memberNo.isEmpty()) {
            memberNo = CommonUtil.createUserNo();
        }

        // 检查会员名称是否重复
        String name = command.getName();
        if (memberDomainService.isMemberNameExists(command.getMerchantId(), name)) {
            name = memberNo;
        }

        // 获取默认会员等级
        Integer gradeId = command.getGradeId();
        if (gradeId == null || gradeId <= 0) {
            MemberGrade initGrade = memberGradeRepository.findInitGrade(command.getMerchantId());
            if (initGrade != null) {
                gradeId = initGrade.getId();
            }
        }

        // 创建会员领域对象
        Member member = Member.create(
                memberNo,
                name,
                command.getMobile(),
                gradeId,
                command.getMerchantId(),
                command.getStoreId(),
                command.getSource(),
                command.getIp()
        );

        // 设置密码
        if (command.getPassword() != null && !command.getPassword().isEmpty()) {
            String salt = SeqUtil.getRandomLetter(4);
            member.setPassword(command.getPassword(), salt);
        }

        // 设置其他属性
        member.updateBasicInfo(
                null,
                command.getAvatar(),
                command.getSex(),
                command.getBirthday(),
                command.getIdcard(),
                command.getCarNo(),
                null,
                null
        );

        if (command.getOpenId() != null && !command.getOpenId().isEmpty()) {
            member.bindOpenId(command.getOpenId());
        }

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
            commissionRelationService.setCommissionRelation(mtUser, command.getShareId());
        } catch (Exception e) {
            logger.error("设置分佣关系失败: memberId={}, error={}", member.getMemberIdValue(), e.getMessage());
        }

        // 发送注册短信
        if (command.getMobile() != null && !command.getMobile().isEmpty()) {
            try {
                List<String> mobileList = new ArrayList<>();
                mobileList.add(command.getMobile());
                Map<String, String> params = new HashMap<>();
                sendSmsService.sendSms(command.getMerchantId(), "register-sms", mobileList, params);
            } catch (Exception e) {
                logger.error("发送注册短信失败: mobile={}, error={}", command.getMobile(), e.getMessage());
            }
        }

        return mtUserMapper.selectById(member.getMemberIdValue());
    }

    /**
     * 更新会员信息
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "更新会员信息")
    public void updateMember(UpdateMemberCommand command) throws BusinessCheckException {
        Member member = memberRepository.findById(MemberId.of(command.getMemberId()));
        if (member == null) {
            throw new BusinessCheckException("会员不存在");
        }

        // 检查会员号是否重复
        if (command.getMemberNo() != null && !command.getMemberNo().isEmpty()) {
            if (!memberDomainService.isMemberNoAvailable(
                    member.getMerchantId(),
                    command.getMemberNo(),
                    command.getMemberId())) {
                throw new BusinessCheckException("该会员号已被使用");
            }
        }

        // 更新手机号
        if (command.getMobile() != null && !command.getMobile().isEmpty()) {
            member.updateMobile(command.getMobile());
            memberRepository.resetMobile(member.getMerchantId(), command.getMobile(), command.getMemberId());
        }

        // 更新基本信息
        member.updateBasicInfo(
                command.getName(),
                command.getAvatar(),
                command.getSex(),
                command.getBirthday(),
                command.getIdcard(),
                command.getCarNo(),
                command.getAddress(),
                command.getGroupId()
        );

        // 更新店铺
        if (command.getStoreId() != null) {
            member.setStore(command.getStoreId());
        }

        // 修改密码
        if (command.getModifyPassword() != null && command.getModifyPassword() &&
                command.getPassword() != null && !command.getPassword().isEmpty()) {
            String salt = SeqUtil.getRandomLetter(4);
            member.setPassword(command.getPassword(), salt);
        }

        // 修改会员等级
        Integer oldGradeId = member.getGradeId();
        if (command.getGradeId() != null && !command.getGradeId().equals(oldGradeId)) {
            MemberGrade newGrade = memberGradeRepository.findById(command.getGradeId());
            if (newGrade != null) {
                member.upgradeGrade(command.getGradeId(), null);
                // 开卡赠礼
                try {
                    openGiftService.openGift(member.getMemberIdValue(), command.getGradeId(), false);
                } catch (Exception e) {
                    logger.error("升级等级开卡赠礼失败: memberId={}, error={}", member.getMemberIdValue(), e.getMessage());
                }
            }
        }

        memberRepository.save(member);
    }

    /**
     * 删除会员
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "删除会员")
    public void deleteMember(DeleteMemberCommand command) throws BusinessCheckException {
        Member member = memberRepository.findById(MemberId.of(command.getMemberId()));
        if (member == null) {
            throw new BusinessCheckException("会员不存在");
        }
        memberRepository.remove(member);
    }

    /**
     * 调整余额
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "调整会员余额")
    public void adjustBalance(AdjustBalanceCommand command) throws BusinessCheckException {
        Member member = memberRepository.findById(MemberId.of(command.getMemberId()));
        if (member == null) {
            throw new BusinessCheckException("会员不存在");
        }

        if (command.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            member.addBalance(command.getAmount());
        } else if (command.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            member.deductBalance(command.getAmount().abs());
        }

        memberRepository.save(member);
    }

    /**
     * 调整积分
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "调整会员积分")
    public void adjustPoint(AdjustPointCommand command) throws BusinessCheckException {
        Member member = memberRepository.findById(MemberId.of(command.getMemberId()));
        if (member == null) {
            throw new BusinessCheckException("会员不存在");
        }

        if (command.getPoints() > 0) {
            member.addPoint(command.getPoints());
        } else if (command.getPoints() < 0) {
            member.deductPoint(Math.abs(command.getPoints()));
        }

        memberRepository.save(member);
    }

    /**
     * 更新活跃时间
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateActiveTime(Integer memberId, String ip) {
        Member member = memberRepository.findById(MemberId.of(memberId));
        if (member != null && member.isActive()) {
            member.updateActiveTime(ip);
            memberRepository.save(member);
        }
    }
}
