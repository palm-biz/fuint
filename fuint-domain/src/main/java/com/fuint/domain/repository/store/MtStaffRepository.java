package com.fuint.domain.repository.store;
import com.fuint.domain.model.store.MtStaff;
import java.util.List;
import java.util.Optional;
public interface MtStaffRepository {
    MtStaff save(MtStaff entity);
    Optional<MtStaff> findById(Long id);
    List<MtStaff> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtStaff> findByStoreId(Long storeId);
}
