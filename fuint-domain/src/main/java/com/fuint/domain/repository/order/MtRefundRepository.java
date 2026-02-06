package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtRefund;
import java.util.List;
import java.util.Optional;

public interface MtRefundRepository {
    MtRefund save(MtRefund mtRefund);
    Optional<MtRefund> findById(Long id);
    List<MtRefund> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtRefund> findByOrderId(Long orderId);
}
