package cloud.palmbiz.domain.member.repository;

import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.model.MemberId;
import cloud.palmbiz.common.param.MemberPage;

import java.util.Date;
import java.util.List;

/**
 * 会员仓储接口
 * 领域层定义，基础设施层实现
 *
 * @author DDD Refactoring
 */
public interface MemberRepository {

    /**
     * 根据ID查找会员
     */
    Member findById(MemberId id);

    /**
     * 根据手机号查找会员
     */
    Member findByMobile(Integer merchantId, String mobile);

    /**
     * 根据会员号查找会员
     */
    Member findByMemberNo(Integer merchantId, String memberNo);

    /**
     * 根据会员名称查找会员
     */
    Member findByName(Integer merchantId, String name);

    /**
     * 根据OpenID查找会员
     */
    Member findByOpenId(Integer merchantId, String openId);

    /**
     * 分页查询会员列表
     */
    List<Member> findByPage(MemberPage memberPage);

    /**
     * 获取分页查询的总记录数
     */
    long getPageTotal();

    /**
     * 获取分页查询的总页数
     */
    int getPageCount();

    /**
     * 保存会员（新建或更新）
     */
    void save(Member member);

    /**
     * 删除会员（软删除）
     */
    void remove(Member member);

    /**
     * 获取会员数量
     */
    Long getMemberCount(Integer merchantId, Integer storeId);

    /**
     * 获取会员数量（按时间范围）
     */
    Long getMemberCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime);

    /**
     * 获取会员ID列表
     */
    List<Integer> getMemberIdList(Integer merchantId, Integer storeId);

    /**
     * 搜索会员
     */
    List<Member> searchMembers(Integer merchantId, String keyword);

    /**
     * 更新活跃时间
     */
    void updateActiveTime(Integer memberId, Date activeTime);

    /**
     * 重置手机号
     */
    void resetMobile(Integer merchantId, String mobile, Integer memberId);
}
