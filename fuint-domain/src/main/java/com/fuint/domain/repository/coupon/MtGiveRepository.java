package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtGive;

import java.util.List;
import java.util.Optional;

/**
 * 转赠记录仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtGiveRepository {

    MtGive save(MtGive mtGive);

    Optional<MtGive> findById(Long id);

    List<MtGive> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtGive> findByUserId(Long userId);
}
