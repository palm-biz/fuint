package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtSmsSendedLog;
import java.util.List;
import java.util.Optional;
public interface MtSmsSendedLogRepository {
    MtSmsSendedLog save(MtSmsSendedLog entity);
    Optional<MtSmsSendedLog> findById(Long id);
    List<MtSmsSendedLog> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtSmsSendedLog> findByMobile(String mobile);
}
