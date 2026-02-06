package com.fuint.domain.repository.commission;
import com.fuint.domain.model.commission.MtCommissionRuleItem;
import java.util.List;
import java.util.Optional;
public interface MtCommissionRuleItemRepository {
    MtCommissionRuleItem save(MtCommissionRuleItem entity);
    Optional<MtCommissionRuleItem> findById(Long id);
    List<MtCommissionRuleItem> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtCommissionRuleItem> findByRuleId(Long ruleId);
}
