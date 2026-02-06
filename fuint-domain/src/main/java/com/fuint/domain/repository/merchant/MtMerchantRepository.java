package com.fuint.domain.repository.merchant;
import com.fuint.domain.model.merchant.MtMerchant;
import java.util.List;
import java.util.Optional;
public interface MtMerchantRepository {
    MtMerchant save(MtMerchant entity);
    Optional<MtMerchant> findById(Long id);
    List<MtMerchant> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtMerchant> findByStatus(String status);
}
