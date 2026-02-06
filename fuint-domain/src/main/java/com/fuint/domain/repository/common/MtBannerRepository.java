package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtBanner;
import java.util.List;
import java.util.Optional;
public interface MtBannerRepository {
    MtBanner save(MtBanner entity);
    Optional<MtBanner> findById(Long id);
    List<MtBanner> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtBanner> findByStatus(String status);
}
