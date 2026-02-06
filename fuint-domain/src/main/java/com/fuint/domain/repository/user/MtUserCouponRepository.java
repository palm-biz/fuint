package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtUserCoupon;

import java.util.List;
import java.util.Optional;

/**
 * 会员卡券仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtUserCouponRepository {

    MtUserCoupon save(MtUserCoupon mtUserCoupon);

    Optional<MtUserCoupon> findById(Long id);

    List<MtUserCoupon> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtUserCoupon> findByUserId(Long userId);
}
