package com.fuint.domain.repository.goods;
import com.fuint.domain.model.goods.MtGoodsSpec;
import java.util.List;
import java.util.Optional;
public interface MtGoodsSpecRepository {
    MtGoodsSpec save(MtGoodsSpec entity);
    Optional<MtGoodsSpec> findById(Long id);
    List<MtGoodsSpec> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtGoodsSpec> findByGoodsId(Long goodsId);
}
