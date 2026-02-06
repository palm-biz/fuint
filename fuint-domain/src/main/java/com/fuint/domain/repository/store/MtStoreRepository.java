package com.fuint.domain.repository.store;
import com.fuint.domain.model.store.MtStore;
import java.util.List;
import java.util.Optional;
public interface MtStoreRepository {
    MtStore save(MtStore entity);
    Optional<MtStore> findById(Long id);
    List<MtStore> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtStore> findByStatus(String status);
}
