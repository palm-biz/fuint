package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtInvoice;
import java.util.List;
import java.util.Optional;
public interface MtInvoiceRepository {
    MtInvoice save(MtInvoice entity);
    Optional<MtInvoice> findById(Long id);
    List<MtInvoice> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtInvoice> findByOrderId(Long orderId);
}
