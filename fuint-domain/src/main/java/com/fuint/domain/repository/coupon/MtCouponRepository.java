package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtCoupon;

import java.util.List;
import java.util.Optional;

/**
 * 卡券仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtCouponRepository {

    MtCoupon save(MtCoupon mtCoupon);

    Optional<MtCoupon> findById(Long id);

    List<MtCoupon> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtCoupon> findByStatus(String status);

    List<MtCoupon> findByGroupId(Long groupId);
}
