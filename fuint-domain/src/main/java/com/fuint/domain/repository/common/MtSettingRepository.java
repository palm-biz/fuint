package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtSetting;
import java.util.List;
import java.util.Optional;
public interface MtSettingRepository {
    MtSetting save(MtSetting entity);
    Optional<MtSetting> findById(Long id);
    List<MtSetting> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtSetting> findByType(String type);
}
