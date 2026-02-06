package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtRegion;
import java.util.List;
import java.util.Optional;
public interface MtRegionRepository {
    MtRegion save(MtRegion entity);
    Optional<MtRegion> findById(Long id);
    List<MtRegion> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtRegion> findByParentId(Long parentId);
}
