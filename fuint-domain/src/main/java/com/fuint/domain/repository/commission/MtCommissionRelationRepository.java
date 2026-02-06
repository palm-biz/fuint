package com.fuint.domain.repository.commission;
import com.fuint.domain.model.commission.MtCommissionRelation;
import java.util.List;
import java.util.Optional;
public interface MtCommissionRelationRepository {
    MtCommissionRelation save(MtCommissionRelation entity);
    Optional<MtCommissionRelation> findById(Long id);
    List<MtCommissionRelation> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtCommissionRelation> findByUserId(Long userId);
}
