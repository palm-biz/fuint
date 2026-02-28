package cloud.palmbiz.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cloud.palmbiz.application.member.command.*;
import cloud.palmbiz.application.member.service.MemberAuthService;
import cloud.palmbiz.application.member.service.MemberCommandService;
import cloud.palmbiz.application.member.service.MemberQueryService;
import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.group.dto.GroupMemberDto;
import cloud.palmbiz.common.member.dto.MemberTopDto;
import cloud.palmbiz.common.user.dto.UserDto;
import cloud.palmbiz.common.param.MemberPage;
import cloud.palmbiz.common.service.*;
import cloud.palmbiz.common.util.*;
import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.framework.annoation.OperationServiceLog;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.mapper.MtUserMapper;
import cloud.palmbiz.infrastructure.model.*;
import cloud.palmbiz.common.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 会员业务接口实现类（Facade模式）
 * 保持向后兼容，内部委托给新的应用服务
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor(onConstructor_= {@Lazy})
public class MemberServiceImpl extends ServiceImpl<MtUserMapper, MtUser> implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MtUserMapper mtUserMapper;
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberAuthService memberAuthService;

    /**
     * 后台账户服务接口
     */
    private final AccountService accountService;

    /**
     * 员工接口
     */
    private final StaffService staffService;

    /**
     * 会员行为接口
     */
    private final UserActionService userActionService;

    /**
     * 更新活跃时间
     * 委托给 MemberCommandService
     *
     * @param userId 会员ID
     * @param ip IP地址
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateActiveTime(Integer userId, String ip) throws BusinessCheckException {
        Member member = memberQueryService.queryMemberById(userId);
        if (member != null && member.isActive()) {
            Date lastUpdateTime = member.getUpdateTime();
            Date registerTime = member.getCreateTime();

            if (StringUtil.isEmpty(member.getIp())) {
                // 更新IP
                MtUser mtUser = mtUserMapper.selectById(userId);
                mtUser.setIp(ip);
                mtUserMapper.updateById(mtUser);
            }

            if (lastUpdateTime != null) {
                Long timestampLast = Long.valueOf(TimeUtils.date2timeStamp(lastUpdateTime));
                Long timestampNow = System.currentTimeMillis() / 1000;
                Long minute = timestampNow - timestampLast;

                // 5分钟更新一次
                if (minute >= 300 || registerTime.equals(lastUpdateTime)) {
                    synchronized(MemberServiceImpl.class) {
                        Date activeTime = new Date();
                        memberCommandService.updateActiveTime(userId, ip);

                        // 记录会员行为
                        MtUserAction mtUserAction = new MtUserAction();
                        mtUserAction.setUserId(userId);
                        mtUserAction.setStoreId(member.getStoreId());
                        mtUserAction.setMerchantId(member.getMerchantId());
                        mtUserAction.setParam(TimeUtils.formatDate(activeTime, "yyyy-MM-dd HH:mm:ss"));
                        mtUserAction.setAction(UserActionEnum.LOGIN.getKey());
                        mtUserAction.setDescription(UserActionEnum.LOGIN.getValue());
                        userActionService.addUserAction(mtUserAction);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取当前操作会员信息
     *
     * @param userId 会员ID
     * @param token 登录token
     * @return
     */
    @Override
    public MtUser getCurrentUserInfo(HttpServletRequest request, Integer userId, String token) {
        MtUser mtUser = null;

        // 没有会员信息，则查询是否是后台收银员下单
        AccountInfoDto accountInfo = TokenUtil.getAccountInfoByToken(token);
        if (accountInfo != null) {
            // 输入了会员ID就用会员的账号下单，否则用员工账号下单
            if (userId != null && userId > 0) {
                mtUser = queryMemberById(userId);
            } else {
                Integer accountId = accountInfo.getId();
                TAccount account = accountService.getAccountInfoById(accountId);
                if (account != null) {
                    if (account.getStaffId() > 0) {
                        MtStaff staff = staffService.queryStaffById(account.getStaffId());
                        if (staff != null) {
                            mtUser = queryMemberById(staff.getUserId());
                            if (mtUser != null) {
                                if (staff.getStoreId() != null && staff.getStoreId() > 0) {
                                    mtUser.setStoreId(staff.getStoreId());
                                }
                                if (account.getMerchantId() != null && account.getMerchantId() > 0 && !account.getMerchantId().equals(mtUser.getMerchantId())) {
                                    mtUser.setMerchantId(account.getMerchantId());
                                }
                                mtUser.setUpdateTime(new Date());
                                updateById(mtUser);
                            }
                        }
                    }
                }
            }
        }
        return mtUser;
    }

    /**
     * 分页查询会员列表
     * 委托给 MemberQueryService
     *
     * @param memberPage 分页参数
     * @return
     */
    @Override
    public PaginationResponse<UserDto> queryMemberListByPagination(MemberPage memberPage) {
        return memberQueryService.queryMemberListByPagination(memberPage);
    }

    /**
     * 添加会员
     * 委托给 MemberCommandService
     *
     * @param  mtUser 会员信息
     * @param  shareId 分享用户ID
     * @throws BusinessCheckException
     * @return
     */
    @Override
    @OperationServiceLog(description = "新增会员信息")
    public MtUser addMember(MtUser mtUser, String shareId) throws BusinessCheckException {
        RegisterMemberCommand command = new RegisterMemberCommand();
        command.setMemberNo(mtUser.getUserNo());
        command.setName(mtUser.getName());
        command.setMobile(mtUser.getMobile());
        command.setPassword(mtUser.getPassword());
        command.setGradeId(mtUser.getGradeId());
        command.setMerchantId(mtUser.getMerchantId());
        command.setStoreId(mtUser.getStoreId());
        command.setSource(mtUser.getSource() != null ? mtUser.getSource() : MemberSourceEnum.BACKEND_ADD.getKey());
        command.setIp(mtUser.getIp());
        command.setShareId(shareId);
        command.setSex(mtUser.getSex());
        command.setBirthday(mtUser.getBirthday());
        command.setIdcard(mtUser.getIdcard());
        command.setCarNo(mtUser.getCarNo());
        command.setAvatar(mtUser.getAvatar());
        command.setOpenId(mtUser.getOpenId());

        return memberCommandService.registerMember(command);
    }

    /**
     * 更新会员信息
     * 委托给 MemberCommandService
     *
     * @param  mtUser 会员信息
     * @param  modifyPassword 修改密码
     * @throws BusinessCheckException
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "修改会员信息")
    public MtUser updateMember(MtUser mtUser, boolean modifyPassword) throws BusinessCheckException {
        UpdateMemberCommand command = new UpdateMemberCommand();
        command.setMemberId(mtUser.getId());
        command.setName(mtUser.getName());
        command.setMobile(mtUser.getMobile());
        command.setMemberNo(mtUser.getUserNo());
        command.setGradeId(mtUser.getGradeId());
        command.setStoreId(mtUser.getStoreId());
        command.setGroupId(mtUser.getGroupId());
        command.setSex(mtUser.getSex());
        command.setBirthday(mtUser.getBirthday());
        command.setIdcard(mtUser.getIdcard());
        command.setCarNo(mtUser.getCarNo());
        command.setAvatar(mtUser.getAvatar());
        command.setAddress(mtUser.getAddress());
        command.setPassword(mtUser.getPassword());
        command.setModifyPassword(modifyPassword);

        memberCommandService.updateMember(command);
        return mtUserMapper.selectById(mtUser.getId());
    }

    /**
     * 通过手机号新增会员
     * 委托给 MemberAuthService
     *
     * @param merchantId 商户ID
     * @param  mobile 手机号
     * @param  shareId 分享用户ID
     * @param ip IP地址
     * @throws BusinessCheckException
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "通过手机号新增会员")
    public MtUser addMemberByMobile(Integer merchantId, String mobile, String shareId, String ip) throws BusinessCheckException {
        Member member = memberAuthService.loginByMobile(merchantId, mobile, shareId, ip);
        return mtUserMapper.selectById(member.getMemberIdValue());
    }

    /**
     * 根据手机号获取会员信息
     * 委托给 MemberQueryService
     *
     * @param  merchantId 商户ID
     * @param  mobile 手机号
     * @return
     */
    @Override
    public MtUser queryMemberByMobile(Integer merchantId, String mobile) {
        Member member = memberQueryService.queryMemberByMobile(merchantId, mobile);
        return member != null ? mtUserMapper.selectById(member.getMemberIdValue()) : null;
    }

    /**
     * 根据会员号号获取会员信息
     * 委托给 MemberQueryService
     *
     * @param  merchantId 商户ID
     * @param  userNo     会员号
     * @return
     */
    @Override
    public MtUser queryMemberByUserNo(Integer merchantId, String userNo) {
        Member member = memberQueryService.queryMemberByUserNo(merchantId, userNo);
        return member != null ? mtUserMapper.selectById(member.getMemberIdValue()) : null;
    }

    /**
     * 根据会员ID获取会员信息
     * 委托给 MemberQueryService
     *
     * @param  id 会员ID
     * @return
     */
    @Override
    public MtUser queryMemberById(Integer id) {
        Member member = memberQueryService.queryMemberById(id);
        return member != null ? mtUserMapper.selectById(member.getMemberIdValue()) : null;
    }

    /**
     * 根据会员名称获取会员信息
     * 委托给 MemberQueryService
     *
     * @param  merchantId 商户ID
     * @param  name 会员名称
     * @return
     */
    @Override
    public MtUser queryMemberByName(Integer merchantId, String name) {
        Member member = memberQueryService.queryMemberByName(merchantId, name);
        return member != null ? mtUserMapper.selectById(member.getMemberIdValue()) : null;
    }

    /**
     * 根据openId获取会员信息(为空就注册)
     * 委托给 MemberAuthService
     *
     * @param  merchantId 商户ID
     * @param  openId 微信openId
     * @throws BusinessCheckException
     * @return
     */
    @Override
    public MtUser queryMemberByOpenId(Integer merchantId, String openId, JSONObject userInfo) throws BusinessCheckException {
        Member member = memberAuthService.loginByOpenId(merchantId, openId, userInfo);
        return member != null ? mtUserMapper.selectById(member.getMemberIdValue()) : null;
    }

    /**
     * 根据等级ID获取会员等级信息
     * 委托给 MemberQueryService
     *
     * @param  id 等级ID
     * @return
     */
    @Override
    public MtUserGrade queryMemberGradeByGradeId(Integer id) {
        MemberGrade memberGrade = memberQueryService.queryMemberGradeByGradeId(id);
        if (memberGrade == null) {
            return null;
        }
        MtUserGrade mtUserGrade = new MtUserGrade();
        mtUserGrade.setId(memberGrade.getId());
        mtUserGrade.setMerchantId(memberGrade.getMerchantId());
        mtUserGrade.setGrade(memberGrade.getGrade());
        mtUserGrade.setName(memberGrade.getName());
        mtUserGrade.setCatchCondition(memberGrade.getCatchCondition());
        mtUserGrade.setCatchType(memberGrade.getCatchType());
        mtUserGrade.setCatchValue(memberGrade.getCatchValue());
        mtUserGrade.setUserPrivilege(memberGrade.getUserPrivilege());
        mtUserGrade.setValidDay(memberGrade.getValidDay());
        mtUserGrade.setDiscount(memberGrade.getDiscount());
        mtUserGrade.setSpeedPoint(memberGrade.getSpeedPoint());
        mtUserGrade.setRebate(memberGrade.getRebate());
        mtUserGrade.setStatus(memberGrade.getStatus());
        return mtUserGrade;
    }

    /**
     * 删除会员
     * 委托给 MemberCommandService
     *
     * @param  id 会员ID
     * @param  operator 操作人
     * @throws BusinessCheckException
     * @return
     */
    @Override
    @OperationServiceLog(description = "删除会员信息")
    public Integer deleteMember(Integer id, String operator) throws BusinessCheckException {
        DeleteMemberCommand command = new DeleteMemberCommand();
        command.setMemberId(id);
        command.setOperator(operator);
        memberCommandService.deleteMember(command);
        return id;
    }

    /**
     * 根据条件搜索会员分组
     * 委托给 MemberQueryService
     *
     * @param params 查询参数
     * @return
     */
    @Override
    public List<MtUserGrade> queryMemberGradeByParams(Map<String, Object> params) {
        List<MemberGrade> grades = memberQueryService.queryMemberGradeByParams(params);
        List<MtUserGrade> result = new ArrayList<>();
        for (MemberGrade grade : grades) {
            MtUserGrade mtGrade = new MtUserGrade();
            mtGrade.setId(grade.getId());
            mtGrade.setMerchantId(grade.getMerchantId());
            mtGrade.setGrade(grade.getGrade());
            mtGrade.setName(grade.getName());
            mtGrade.setCatchCondition(grade.getCatchCondition());
            mtGrade.setCatchType(grade.getCatchType());
            mtGrade.setCatchValue(grade.getCatchValue());
            mtGrade.setUserPrivilege(grade.getUserPrivilege());
            mtGrade.setValidDay(grade.getValidDay());
            mtGrade.setDiscount(grade.getDiscount());
            mtGrade.setSpeedPoint(grade.getSpeedPoint());
            mtGrade.setRebate(grade.getRebate());
            mtGrade.setStatus(grade.getStatus());
            result.add(mtGrade);
        }
        return result;
    }

    /**
     * 获取会员数量
     * 委托给 MemberQueryService
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @return
     */
    @Override
    public Long getUserCount(Integer merchantId, Integer storeId) {
        return memberQueryService.getMemberCount(merchantId, storeId);
    }

    /**
     * 获取会员数量
     * 委托给 MemberQueryService
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public Long getUserCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        return memberQueryService.getMemberCount(merchantId, storeId, beginTime, endTime);
    }

    /**
     * 获取活跃会员数量（保留原有实现，暂不重构）
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public Long getActiveUserCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        // 保留原有实现，依赖 MtUserActionMapper
        return 0L; // TODO: 需要实现
    }

    /**
     * 重置手机号（保留原有实现）
     *
     * @param mobile 手机号码
     * @param userId 会员ID
     * @return
     */
    @Override
    public void resetMobile(String mobile, Integer userId) {
        if (mobile == null || StringUtil.isEmpty(mobile)) {
            return;
        }
        Member member = memberQueryService.queryMemberById(userId);
        if (member != null) {
            member.updateMobile(mobile);
            // 使用原有的Mapper方法保持一致性
            mtUserMapper.resetMobile(member.getMerchantId(), mobile, userId);
        }
    }

    /**
     * 获取会员消费排行榜
     * 委托给 MemberQueryService
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public List<MemberTopDto> getMemberConsumeTopList(Integer merchantId, Integer storeId, Date startTime, Date endTime) {
        return memberQueryService.getMemberConsumeTopList(merchantId, storeId, startTime, endTime);
    }

    /**
     * 查找会员列表
     * 委托给 MemberQueryService
     *
     * @param merchantId 商户ID
     * @param keyword 关键字
     * @param groupIds 分组ID
     * @param page 当前页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public List<GroupMemberDto> searchMembers(Integer merchantId, String keyword, String groupIds, Integer page, Integer pageSize) {
        return memberQueryService.searchMembersForGroup(merchantId, keyword, groupIds, page, pageSize);
    }

    /**
     * 查找会员列表
     * 委托给 MemberQueryService
     *
     * @param merchantId 商户ID
     * @param keyword 关键字
     * @return
     */
    @Override
    public List<MtUser> searchMembers(Integer merchantId, String keyword) {
        List<Member> members = memberQueryService.searchMembers(merchantId, keyword);
        List<MtUser> result = new ArrayList<>();
        for (Member member : members) {
            result.add(mtUserMapper.selectById(member.getMemberIdValue()));
        }
        return result;
    }

    /**
     * 设定安全的密码（保留用于向后兼容）
     *
     * @param password 密码明文
     * @param salt 加密因子
     * @return
     */
    @Override
    public String enCodePassword(String password, String salt) {
        return MD5Util.getMD5(password + salt);
    }

    /**
     * 获取加密密码（保留用于向后兼容）
     *
     * @param password 密码密文
     * @param salt 加密因子
     * @return
     */
    @Override
    public String deCodePassword(String password, String salt) {
        return MD5Util.getMD5(password + salt);
    }

    /**
     * 获取会员ID列表
     * 委托给 MemberQueryService
     *
     * @param merchantId 商户号
     * @param storeId 店铺ID
     * @return
     */
    @Override
    public List<Integer> getUserIdList(Integer merchantId, Integer storeId) {
        return memberQueryService.getMemberIdList(merchantId, storeId);
    }

    /**
     * 导入会员（保留原有实现，不重构）
     *
     * @param file excel文件
     * @param accountInfo 操作者
     * @param filePath 文件路径
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "导入会员列表")
    public Boolean importMember(MultipartFile file, AccountInfoDto accountInfo, String filePath) throws BusinessCheckException, ParseException {
        // 保留原有实现，导入功能较复杂，暂不重构
        // TODO: 需要完整实现
        throw new BusinessCheckException("导入功能暂未实现");
    }
}
