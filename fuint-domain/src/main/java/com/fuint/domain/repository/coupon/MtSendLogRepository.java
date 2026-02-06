package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtSendLog;

import java.util.List;
import java.util.Optional;

/**
 * 卡券发放记录仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtSendLogRepository {

    MtSendLog save(MtSendLog mtSendLog);

    Optional<MtSendLog> findById(Long id);

    List<MtSendLog> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtSendLog> findByUserId(Long userId);

    List<MtSendLog> findByCouponId(Long couponId);
}
