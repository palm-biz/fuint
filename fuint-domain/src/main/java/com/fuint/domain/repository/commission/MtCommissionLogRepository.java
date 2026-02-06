package com.fuint.domain.repository.commission;
import com.fuint.domain.model.commission.MtCommissionLog;
import java.util.List;
import java.util.Optional;
public interface MtCommissionLogRepository {
    MtCommissionLog save(MtCommissionLog entity);
    Optional<MtCommissionLog> findById(Long id);
    List<MtCommissionLog> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtCommissionLog> findByUserId(Long userId);
}
