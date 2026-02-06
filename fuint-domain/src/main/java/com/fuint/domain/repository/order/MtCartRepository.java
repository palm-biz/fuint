package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtCart;
import java.util.List;
import java.util.Optional;

public interface MtCartRepository {
    MtCart save(MtCart mtCart);
    Optional<MtCart> findById(Long id);
    List<MtCart> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtCart> findByUserId(Long userId);
}
