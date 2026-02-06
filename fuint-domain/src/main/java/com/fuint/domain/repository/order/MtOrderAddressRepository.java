package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtOrderAddress;
import java.util.List;
import java.util.Optional;

public interface MtOrderAddressRepository {
    MtOrderAddress save(MtOrderAddress mtOrderAddress);
    Optional<MtOrderAddress> findById(Long id);
    List<MtOrderAddress> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    Optional<MtOrderAddress> findByOrderId(Long orderId);
}
