package com.fuint.domain.repository.goods;
import com.fuint.domain.model.goods.MtGoodsSku;
import java.util.List;
import java.util.Optional;
public interface MtGoodsSkuRepository {
    MtGoodsSku save(MtGoodsSku entity);
    Optional<MtGoodsSku> findById(Long id);
    List<MtGoodsSku> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtGoodsSku> findByGoodsId(Long goodsId);
}
