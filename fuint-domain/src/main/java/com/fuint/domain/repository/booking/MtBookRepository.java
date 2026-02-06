package com.fuint.domain.repository.booking;
import com.fuint.domain.model.booking.MtBook;
import java.util.List;
import java.util.Optional;
public interface MtBookRepository {
    MtBook save(MtBook entity);
    Optional<MtBook> findById(Long id);
    List<MtBook> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtBook> findByStatus(String status);
}
