package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtCouponGoods;

import java.util.List;
import java.util.Optional;

/**
 * 卡券适用商品仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtCouponGoodsRepository {

    MtCouponGoods save(MtCouponGoods mtCouponGoods);

    Optional<MtCouponGoods> findById(Long id);

    List<MtCouponGoods> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtCouponGoods> findByCouponId(Long couponId);
}
