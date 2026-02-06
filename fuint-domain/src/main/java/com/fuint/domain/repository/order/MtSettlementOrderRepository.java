package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtSettlementOrder;
import java.util.List;
import java.util.Optional;

public interface MtSettlementOrderRepository {
    MtSettlementOrder save(MtSettlementOrder mtSettlementOrder);
    Optional<MtSettlementOrder> findById(Long id);
    List<MtSettlementOrder> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtSettlementOrder> findBySettlementId(Long settlementId);
}
