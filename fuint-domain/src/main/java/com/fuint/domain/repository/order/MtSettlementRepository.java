package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtSettlement;
import java.util.List;
import java.util.Optional;

public interface MtSettlementRepository {
    MtSettlement save(MtSettlement mtSettlement);
    Optional<MtSettlement> findById(Long id);
    List<MtSettlement> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtSettlement> findByStatus(String status);
}
