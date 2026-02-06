package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtUploadShippingLog;
import java.util.List;
import java.util.Optional;
public interface MtUploadShippingLogRepository {
    MtUploadShippingLog save(MtUploadShippingLog entity);
    Optional<MtUploadShippingLog> findById(Long id);
    List<MtUploadShippingLog> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtUploadShippingLog> findByOrderId(Long orderId);
}
