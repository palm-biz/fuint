package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtOrder;
import java.util.List;
import java.util.Optional;

public interface MtOrderRepository {
    MtOrder save(MtOrder mtOrder);
    Optional<MtOrder> findById(Long id);
    List<MtOrder> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtOrder> findByUserId(Long userId);
    Optional<MtOrder> findByOrderSn(String orderSn);
}
