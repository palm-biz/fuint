package com.fuint.domain.repository.goods;

import com.fuint.domain.model.goods.MtGoods;
import java.util.List;
import java.util.Optional;

public interface MtGoodsRepository {
    MtGoods save(MtGoods mtGoods);
    Optional<MtGoods> findById(Long id);
    List<MtGoods> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtGoods> findByStatus(String status);
    List<MtGoods> findByCateId(Long cateId);
}
