package com.fuint.domain.repository.store;
import com.fuint.domain.model.store.MtPrinter;
import java.util.List;
import java.util.Optional;
public interface MtPrinterRepository {
    MtPrinter save(MtPrinter entity);
    Optional<MtPrinter> findById(Long id);
    List<MtPrinter> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtPrinter> findByStoreId(Long storeId);
}
