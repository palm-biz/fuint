package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtOrderGoods;
import java.util.List;
import java.util.Optional;

public interface MtOrderGoodsRepository {
    MtOrderGoods save(MtOrderGoods mtOrderGoods);
    Optional<MtOrderGoods> findById(Long id);
    List<MtOrderGoods> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtOrderGoods> findByOrderId(Long orderId);
}
