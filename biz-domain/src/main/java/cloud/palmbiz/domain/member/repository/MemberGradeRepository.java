package cloud.palmbiz.domain.member.repository;

import cloud.palmbiz.domain.member.model.MemberGrade;

import java.util.List;
import java.util.Map;

/**
 * 会员等级仓储接口
 * 领域层定义，基础设施层实现
 *
 * @author DDD Refactoring
 */
public interface MemberGradeRepository {

    /**
     * 根据ID查找会员等级
     */
    MemberGrade findById(Integer id);

    /**
     * 根据商户ID查找所有会员等级
     */
    List<MemberGrade> findByMerchantId(Integer merchantId);

    /**
     * 查找初始会员等级
     */
    MemberGrade findInitGrade(Integer merchantId);

    /**
     * 根据条件查询会员等级
     */
    List<MemberGrade> findByParams(Map<String, Object> params);

    /**
     * 保存会员等级
     */
    void save(MemberGrade memberGrade);
}
