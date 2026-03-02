package cloud.palmbiz.application.member.service;

import cloud.palmbiz.common.param.MemberPage;
import cloud.palmbiz.common.user.dto.UserDto;
import cloud.palmbiz.common.group.dto.GroupMemberDto;
import cloud.palmbiz.common.member.dto.MemberTopDto;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.common.util.TimeUtil;
import cloud.palmbiz.application.store.service.StoreService;
import cloud.palmbiz.application.identity.user.service.UserGradeService;
import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.model.MemberId;
import cloud.palmbiz.domain.member.model.MemberGrade;
import cloud.palmbiz.domain.member.repository.MemberRepository;
import cloud.palmbiz.domain.member.repository.MemberGradeRepository;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.bean.MemberTopBean;
import cloud.palmbiz.infrastructure.mapper.MtUserMapper;
import cloud.palmbiz.infrastructure.model.MtStore;
import cloud.palmbiz.infrastructure.model.MtUserGrade;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员查询服务
 * 处理会员的查询操作
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final StoreService storeService;
    private final UserGradeService userGradeService;
    private final MtUserMapper mtUserMapper;

    /**
     * 分页查询会员列表
     */
    public PaginationResponse<UserDto> queryMemberListByPagination(MemberPage memberPage) {
        // 查询会员列表
        List<Member> members = memberRepository.findByPage(memberPage);
        long total = memberRepository.getPageTotal();
        int pages = memberRepository.getPageCount();

        // 转换为DTO
        List<UserDto> dataList = new ArrayList<>();
        for (Member member : members) {
            UserDto userDto = toUserDto(member, memberPage.getMerchantId());
            dataList.add(userDto);
        }

        // 构造分页响应
        PageRequest pageRequest = PageRequest.of(memberPage.getPage(), memberPage.getPageSize());
        PageImpl pageImpl = new PageImpl(dataList, pageRequest, total);
        PaginationResponse<UserDto> paginationResponse = new PaginationResponse(pageImpl, UserDto.class);
        paginationResponse.setTotalPages(pages);
        paginationResponse.setTotalElements(total);
        paginationResponse.setContent(dataList);

        return paginationResponse;
    }

    /**
     * 根据ID查询会员
     */
    public Member queryMemberById(Integer memberId) {
        Member member = memberRepository.findById(MemberId.of(memberId));

        // 检查会员是否过期
        if (member != null && member.isExpired()) {
            MemberGrade initGrade = memberGradeRepository.findInitGrade(member.getMerchantId());
            if (initGrade != null && !member.getGradeId().equals(initGrade.getId())) {
                member.resetToInitGrade(initGrade.getId());
                memberRepository.save(member);
            }
        }

        // 检查会员等级是否为空
        if (member != null && member.getGradeId() == null) {
            MemberGrade initGrade = memberGradeRepository.findInitGrade(member.getMerchantId());
            if (initGrade != null) {
                member.upgradeGrade(initGrade.getId(), null);
                memberRepository.save(member);
            }
        }

        return member;
    }

    /**
     * 根据手机号查询会员
     */
    public Member queryMemberByMobile(Integer merchantId, String mobile) {
        return memberRepository.findByMobile(merchantId, mobile);
    }

    /**
     * 根据会员号查询会员
     */
    public Member queryMemberByUserNo(Integer merchantId, String userNo) {
        return memberRepository.findByMemberNo(merchantId, userNo);
    }

    /**
     * 根据会员名称查询会员
     */
    public Member queryMemberByName(Integer merchantId, String name) {
        return memberRepository.findByName(merchantId, name);
    }

    /**
     * 根据OpenID查询会员
     */
    public Member queryMemberByOpenId(Integer merchantId, String openId) {
        return memberRepository.findByOpenId(merchantId, openId);
    }

    /**
     * 获取会员数量
     */
    public Long getMemberCount(Integer merchantId, Integer storeId) {
        return memberRepository.getMemberCount(merchantId, storeId);
    }

    /**
     * 获取会员数量（按时间范围）
     */
    public Long getMemberCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        return memberRepository.getMemberCount(merchantId, storeId, beginTime, endTime);
    }

    /**
     * 获取会员ID列表
     */
    public List<Integer> getMemberIdList(Integer merchantId, Integer storeId) {
        return memberRepository.getMemberIdList(merchantId, storeId);
    }

    /**
     * 搜索会员
     */
    public List<Member> searchMembers(Integer merchantId, String keyword) {
        return memberRepository.searchMembers(merchantId, keyword);
    }

    /**
     * 搜索会员（返回GroupMemberDto）
     */
    public List<GroupMemberDto> searchMembersForGroup(Integer merchantId, String keyword, String groupIds, Integer page, Integer pageSize) {
        // 使用原有实现的复杂查询逻辑
        List<Member> members = memberRepository.searchMembers(merchantId, keyword);
        List<GroupMemberDto> dataList = new ArrayList<>();

        for (Member member : members) {
            GroupMemberDto memberDto = new GroupMemberDto();
            memberDto.setId(member.getMemberIdValue());
            memberDto.setName(member.getName());
            memberDto.setUserNo(member.getMemberNoValue());
            memberDto.setMobile(CommonUtil.hidePhone(member.getMobileValue()));
            dataList.add(memberDto);
        }

        return dataList;
    }

    /**
     * 获取会员消费排行榜
     */
    public List<MemberTopDto> getMemberConsumeTopList(Integer merchantId, Integer storeId, Date startTime, Date endTime) {
        List<MemberTopBean> memberList = mtUserMapper.getMemberConsumeTopList(merchantId, storeId, startTime, endTime);
        List<MemberTopDto> dataList = new ArrayList<>();
        if (memberList != null && memberList.size() > 0) {
            for (MemberTopBean bean : memberList) {
                MemberTopDto dto = new MemberTopDto();
                BeanUtils.copyProperties(bean, dto);
                dataList.add(dto);
            }
        }
        return dataList;
    }

    /**
     * 根据等级ID查询会员等级
     */
    public MemberGrade queryMemberGradeByGradeId(Integer gradeId) {
        return memberGradeRepository.findById(gradeId);
    }

    /**
     * 根据条件查询会员等级
     */
    public List<MemberGrade> queryMemberGradeByParams(Map<String, Object> params) {
        return memberGradeRepository.findByParams(params);
    }

    // ==================== 转换方法 ====================

    /**
     * 领域对象转 UserDto
     */
    private UserDto toUserDto(Member member, Integer merchantId) {
        UserDto dto = new UserDto();
        dto.setId(member.getMemberIdValue());
        dto.setUserNo(member.getMemberNoValue());
        dto.setAvatar(member.getAvatar());
        dto.setGroupId(member.getGroupId());
        dto.setName(member.getName());
        dto.setMobile(CommonUtil.hidePhone(member.getMobileValue()));
        dto.setIdcard(member.getIdcard());
        dto.setGradeId(member.getGradeId());
        dto.setStartTime(member.getStartTime());
        dto.setEndTime(member.getEndTime());
        dto.setBalance(member.getBalanceValue());
        dto.setPoint(member.getPointValue());
        dto.setSex(member.getSex());
        dto.setBirthday(member.getBirthday());
        dto.setCarNo(member.getCarNo());
        dto.setSource(member.getSource());
        dto.setAddress(member.getAddress());
        dto.setMerchantId(member.getMerchantId());
        dto.setStoreId(member.getStoreId());
        dto.setIsStaff(member.getIsStaff());
        dto.setCreateTime(member.getCreateTime());
        dto.setUpdateTime(member.getUpdateTime());
        dto.setStatus(member.getStatusCode());
        dto.setDescription(member.getDescription());

        // 查询店铺名称
        if (member.getStoreId() != null && member.getStoreId() > 0) {
            MtStore mtStore = storeService.queryStoreById(member.getStoreId());
            if (mtStore != null) {
                dto.setStoreName(mtStore.getName());
            }
        }

        // 查询等级名称
        if (member.getGradeId() != null) {
            Integer mchId = merchantId != null ? merchantId : 0;
            MtUserGrade mtGrade = userGradeService.queryUserGradeById(mchId, member.getGradeId(), member.getMemberIdValue());
            if (mtGrade != null) {
                dto.setGradeName(mtGrade.getName());
            }
        }

        // 最后登录时间
        dto.setLastLoginTime(TimeUtil.showTime(new Date(), member.getUpdateTime()));

        return dto;
    }
}
