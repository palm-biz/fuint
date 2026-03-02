package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.common.param.MemberPage;
import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.model.MemberId;
import cloud.palmbiz.domain.member.repository.MemberRepository;
import cloud.palmbiz.infrastructure.mapper.MtUserMapper;
import cloud.palmbiz.infrastructure.model.MtUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.enums.YesOrNoEnum;
import cloud.palmbiz.common.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员仓储实现
 * 实现领域层定义的 MemberRepository 接口
 *
 * @author DDD Refactoring
 */
@Repository
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MtUserMapper mtUserMapper;
    private ThreadLocal<Page<?>> pageThreadLocal = new ThreadLocal<>();

    @Override
    public Member findById(MemberId id) {
        MtUser mtUser = mtUserMapper.selectById(id.getValue());
        if (mtUser == null) {
            return null;
        }
        return toDomain(mtUser);
    }

    @Override
    public Member findByMobile(Integer merchantId, String mobile) {
        List<MtUser> userList = mtUserMapper.queryMemberByMobile(merchantId, mobile);
        if (userList == null || userList.isEmpty()) {
            return null;
        }
        return toDomain(userList.get(0));
    }

    @Override
    public Member findByMemberNo(Integer merchantId, String memberNo) {
        List<MtUser> userList = mtUserMapper.findMembersByUserNo(merchantId, memberNo);
        if (userList == null || userList.isEmpty()) {
            return null;
        }
        return toDomain(userList.get(0));
    }

    @Override
    public Member findByName(Integer merchantId, String name) {
        List<MtUser> userList = mtUserMapper.queryMemberByName(merchantId, name);
        if (userList == null || userList.size() != 1) {
            return null;
        }
        return toDomain(userList.get(0));
    }

    @Override
    public Member findByOpenId(Integer merchantId, String openId) {
        MtUser mtUser = mtUserMapper.queryMemberByOpenId(merchantId, openId);
        if (mtUser == null) {
            return null;
        }
        return toDomain(mtUser);
    }

    @Override
    public List<Member> findByPage(MemberPage memberPage) {
        Page<?> pageHelper = PageHelper.startPage(memberPage.getPage(), memberPage.getPageSize());
        pageThreadLocal.set(pageHelper);

        LambdaQueryWrapper<MtUser> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(MtUser::getStatus, StatusEnum.DISABLE.getKey());
        wrapper.eq(MtUser::getIsStaff, YesOrNoEnum.NO.getKey());

        // 构建查询条件
        String name = memberPage.getName();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(MtUser::getName, name);
        }
        Integer userId = memberPage.getId();
        if (userId != null && userId > 0) {
            wrapper.eq(MtUser::getId, userId);
        }
        String keyword = memberPage.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(wq -> wq
                    .eq(MtUser::getMobile, keyword)
                    .or()
                    .eq(MtUser::getUserNo, keyword)
                    .or()
                    .eq(MtUser::getName, keyword));
        }
        String mobile = memberPage.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            wrapper.like(MtUser::getMobile, mobile);
        }
        String birthday = memberPage.getBirthday();
        if (StringUtils.isNotBlank(birthday)) {
            wrapper.like(MtUser::getBirthday, birthday);
        }
        String userNo = memberPage.getUserNo();
        if (StringUtils.isNotBlank(userNo)) {
            wrapper.eq(MtUser::getUserNo, userNo);
        }
        Integer gradeId = memberPage.getGradeId();
        if (gradeId != null && gradeId > 0) {
            wrapper.eq(MtUser::getGradeId, gradeId);
        }
        Integer merchantId = memberPage.getMerchantId();
        if (merchantId != null && merchantId > 0) {
            wrapper.eq(MtUser::getMerchantId, merchantId);
        }
        Integer storeId = memberPage.getStoreId();
        if (storeId != null && storeId > 0) {
            wrapper.eq(MtUser::getStoreId, storeId);
        }
        String storeIds = memberPage.getStoreIds();
        if (StringUtils.isNotBlank(storeIds)) {
            List<String> idList = Arrays.asList(storeIds.split(","));
            if (idList.size() > 0) {
                wrapper.in(MtUser::getStoreId, idList);
            }
        }
        String groupIds = memberPage.getGroupIds();
        if (StringUtils.isNotBlank(groupIds)) {
            List<String> idList = Arrays.asList(groupIds.split(","));
            if (idList.size() > 0) {
                wrapper.in(MtUser::getGroupId, idList);
            }
        }
        String status = memberPage.getStatus();
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(MtUser::getStatus, status);
        }
        // 注册开始、结束时间
        String startTime = memberPage.getStartTime();
        String endTime = memberPage.getEndTime();
        if (StringUtil.isNotEmpty(startTime)) {
            wrapper.ge(MtUser::getCreateTime, startTime);
        }
        if (StringUtil.isNotEmpty(endTime)) {
            wrapper.le(MtUser::getCreateTime, endTime);
        }
        // 注册时间
        String regTime = memberPage.getRegTime();
        if (StringUtil.isNotEmpty(regTime)) {
            String[] dateTime = regTime.split("~");
            if (dateTime.length == 2) {
                wrapper.ge(MtUser::getCreateTime, dateTime[0]);
                wrapper.le(MtUser::getCreateTime, dateTime[1]);
            }
        }
        // 活跃时间
        String activeTime = memberPage.getActiveTime();
        if (StringUtil.isNotEmpty(activeTime)) {
            String[] dateTime = activeTime.split("~");
            if (dateTime.length == 2) {
                wrapper.ge(MtUser::getUpdateTime, dateTime[0]);
                wrapper.le(MtUser::getUpdateTime, dateTime[1]);
            }
        }
        // 会员有效期
        String memberTime = memberPage.getMemberTime();
        if (StringUtil.isNotEmpty(memberTime)) {
            String[] dateTime = memberTime.split("~");
            if (dateTime.length == 2) {
                wrapper.ge(MtUser::getStartTime, dateTime[0]);
                wrapper.le(MtUser::getEndTime, dateTime[1]);
            }
        }
        wrapper.orderByDesc(MtUser::getUpdateTime);

        List<MtUser> userList = mtUserMapper.selectList(wrapper);
        return userList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long getPageTotal() {
        Page<?> page = pageThreadLocal.get();
        if (page != null) {
            long total = page.getTotal();
            pageThreadLocal.remove();
            return total;
        }
        return 0;
    }

    @Override
    public int getPageCount() {
        Page<?> page = pageThreadLocal.get();
        if (page != null) {
            int pages = page.getPages();
            pageThreadLocal.remove();
            return pages;
        }
        return 0;
    }

    @Override
    public void save(Member member) {
        if (member.getMemberId() == null) {
            // 新建
            MtUser mtUser = toPO(member);
            mtUserMapper.insert(mtUser);
            member.setMemberId(mtUser.getId());
        } else {
            // 更新
            MtUser mtUser = toPO(member);
            mtUserMapper.updateById(mtUser);
        }
    }

    @Override
    public void remove(Member member) {
        member.delete();
        save(member);
    }

    @Override
    public Long getMemberCount(Integer merchantId, Integer storeId) {
        if (storeId != null && storeId > 0) {
            return mtUserMapper.getStoreUserCount(storeId);
        } else {
            return mtUserMapper.getUserCount(merchantId);
        }
    }

    @Override
    public Long getMemberCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        if (storeId != null && storeId > 0) {
            return mtUserMapper.getStoreUserCountByTime(storeId, beginTime, endTime);
        } else {
            return mtUserMapper.getUserCountByTime(merchantId, beginTime, endTime);
        }
    }

    @Override
    public List<Integer> getMemberIdList(Integer merchantId, Integer storeId) {
        return mtUserMapper.getUserIdList(merchantId, storeId);
    }

    @Override
    public List<Member> searchMembers(Integer merchantId, String keyword) {
        List<MtUser> userList = mtUserMapper.searchMembers(merchantId, keyword);
        return userList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateActiveTime(Integer memberId, Date activeTime) {
        mtUserMapper.updateActiveTime(memberId, activeTime);
    }

    @Override
    public void resetMobile(Integer merchantId, String mobile, Integer memberId) {
        mtUserMapper.resetMobile(merchantId, mobile, memberId);
    }

    // ==================== 转换方法 ====================

    /**
     * PO 转领域对象
     */
    private Member toDomain(MtUser po) {
        return Member.reconstitute(
                po.getId(),
                po.getUserNo(),
                po.getAvatar(),
                po.getGroupId(),
                po.getName(),
                po.getOpenId(),
                po.getMobile(),
                po.getIdcard(),
                po.getGradeId(),
                po.getStartTime(),
                po.getEndTime(),
                po.getBalance(),
                po.getPoint(),
                po.getSex(),
                po.getBirthday(),
                po.getCarNo(),
                po.getSource(),
                po.getPassword(),
                po.getSalt(),
                po.getAddress(),
                po.getMerchantId(),
                po.getStoreId(),
                po.getIsStaff(),
                po.getCreateTime(),
                po.getUpdateTime(),
                po.getStatus(),
                po.getDescription(),
                po.getIp(),
                po.getOperator()
        );
    }

    /**
     * 领域对象转 PO
     */
    private MtUser toPO(Member domain) {
        MtUser po = new MtUser();
        if (domain.getMemberId() != null) {
            po.setId(domain.getMemberIdValue());
        }
        po.setUserNo(domain.getMemberNoValue());
        po.setAvatar(domain.getAvatar());
        po.setGroupId(domain.getGroupId());
        po.setName(domain.getName());
        po.setOpenId(domain.getOpenId());
        po.setMobile(domain.getMobileValue());
        po.setIdcard(domain.getIdcard());
        po.setGradeId(domain.getGradeId());
        po.setStartTime(domain.getStartTime());
        po.setEndTime(domain.getEndTime());
        po.setBalance(domain.getBalanceValue());
        po.setPoint(domain.getPointValue());
        po.setSex(domain.getSex());
        po.setBirthday(domain.getBirthday());
        po.setCarNo(domain.getCarNo());
        po.setSource(domain.getSource());
        po.setPassword(domain.getPasswordValue());
        po.setSalt(domain.getSalt());
        po.setAddress(domain.getAddress());
        po.setMerchantId(domain.getMerchantId());
        po.setStoreId(domain.getStoreId());
        po.setIsStaff(domain.getIsStaff());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        po.setStatus(domain.getStatusCode());
        po.setDescription(domain.getDescription());
        po.setIp(domain.getIp());
        po.setOperator(domain.getOperator());
        return po;
    }
}
