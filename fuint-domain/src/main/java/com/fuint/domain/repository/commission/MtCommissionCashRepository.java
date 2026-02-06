package com.fuint.domain.repository.commission;
import com.fuint.domain.model.commission.MtCommissionCash;
import java.util.List;
import java.util.Optional;
public interface MtCommissionCashRepository {
    MtCommissionCash save(MtCommissionCash entity);
    Optional<MtCommissionCash> findById(Long id);
    List<MtCommissionCash> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtCommissionCash> findByUserId(Long userId);
}
