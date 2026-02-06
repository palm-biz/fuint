package com.fuint.domain.repository.order;

import com.fuint.domain.model.order.MtConfirmLog;
import java.util.List;
import java.util.Optional;

public interface MtConfirmLogRepository {
    MtConfirmLog save(MtConfirmLog mtConfirmLog);
    Optional<MtConfirmLog> findById(Long id);
    List<MtConfirmLog> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtConfirmLog> findByUserCouponId(Long userCouponId);
}
