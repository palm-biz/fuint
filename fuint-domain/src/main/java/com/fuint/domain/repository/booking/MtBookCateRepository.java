package com.fuint.domain.repository.booking;
import com.fuint.domain.model.booking.MtBookCate;
import java.util.List;
import java.util.Optional;
public interface MtBookCateRepository {
    MtBookCate save(MtBookCate entity);
    Optional<MtBookCate> findById(Long id);
    List<MtBookCate> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtBookCate> findByStatus(String status);
}
