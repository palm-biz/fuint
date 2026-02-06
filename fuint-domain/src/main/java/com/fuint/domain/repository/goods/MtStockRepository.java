package com.fuint.domain.repository.goods;
import com.fuint.domain.model.goods.MtStock;
import java.util.List;
import java.util.Optional;
public interface MtStockRepository {
    MtStock save(MtStock entity);
    Optional<MtStock> findById(Long id);
    List<MtStock> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtStock> findByStatus(String status);
}
