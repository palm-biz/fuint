package com.fuint.domain.repository.commission;
import com.fuint.domain.model.commission.MtCommissionRule;
import java.util.List;
import java.util.Optional;
public interface MtCommissionRuleRepository {
    MtCommissionRule save(MtCommissionRule entity);
    Optional<MtCommissionRule> findById(Long id);
    List<MtCommissionRule> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtCommissionRule> findByStatus(String status);
}
