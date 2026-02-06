package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtPoint;

import java.util.List;
import java.util.Optional;

/**
 * 会员积分明细仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtPointRepository {

    MtPoint save(MtPoint mtPoint);

    Optional<MtPoint> findById(Long id);

    List<MtPoint> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtPoint> findByUserId(Long userId);
}
