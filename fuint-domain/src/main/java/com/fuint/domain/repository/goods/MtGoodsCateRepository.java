package com.fuint.domain.repository.goods;
import com.fuint.domain.model.goods.MtGoodsCate;
import java.util.List;
import java.util.Optional;
public interface MtGoodsCateRepository {
    MtGoodsCate save(MtGoodsCate entity);
    Optional<MtGoodsCate> findById(Long id);
    List<MtGoodsCate> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtGoodsCate> findByStatus(String status);
}
