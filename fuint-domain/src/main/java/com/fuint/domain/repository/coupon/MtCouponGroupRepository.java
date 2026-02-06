package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtCouponGroup;

import java.util.List;
import java.util.Optional;

/**
 * 卡券分组仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtCouponGroupRepository {

    MtCouponGroup save(MtCouponGroup mtCouponGroup);

    Optional<MtCouponGroup> findById(Long id);

    List<MtCouponGroup> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtCouponGroup> findByStatus(String status);
}
