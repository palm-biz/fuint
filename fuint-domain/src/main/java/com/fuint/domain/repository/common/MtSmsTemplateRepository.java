package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtSmsTemplate;
import java.util.List;
import java.util.Optional;
public interface MtSmsTemplateRepository {
    MtSmsTemplate save(MtSmsTemplate entity);
    Optional<MtSmsTemplate> findById(Long id);
    List<MtSmsTemplate> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtSmsTemplate> findByType(String type);
}
