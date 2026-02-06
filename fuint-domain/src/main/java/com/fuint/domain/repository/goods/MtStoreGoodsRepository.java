package com.fuint.domain.repository.goods;
import com.fuint.domain.model.goods.MtStoreGoods;
import java.util.List;
import java.util.Optional;
public interface MtStoreGoodsRepository {
    MtStoreGoods save(MtStoreGoods entity);
    Optional<MtStoreGoods> findById(Long id);
    List<MtStoreGoods> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtStoreGoods> findByStoreId(Long storeId);
}
