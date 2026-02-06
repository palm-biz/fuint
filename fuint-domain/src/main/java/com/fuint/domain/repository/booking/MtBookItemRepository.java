package com.fuint.domain.repository.booking;
import com.fuint.domain.model.booking.MtBookItem;
import java.util.List;
import java.util.Optional;
public interface MtBookItemRepository {
    MtBookItem save(MtBookItem entity);
    Optional<MtBookItem> findById(Long id);
    List<MtBookItem> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtBookItem> findByBookId(Long bookId);
}
