package com.fuint.domain.repository.goods;
import com.fuint.domain.model.goods.MtStockItem;
import java.util.List;
import java.util.Optional;
public interface MtStockItemRepository {
    MtStockItem save(MtStockItem entity);
    Optional<MtStockItem> findById(Long id);
    List<MtStockItem> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtStockItem> findByStockId(Long stockId);
}
