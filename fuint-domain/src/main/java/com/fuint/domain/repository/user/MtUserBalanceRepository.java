package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtUserBalance;

import java.util.List;
import java.util.Optional;

/**
 * 会员余额仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtUserBalanceRepository {

    MtUserBalance save(MtUserBalance mtUserBalance);

    Optional<MtUserBalance> findById(Long id);

    List<MtUserBalance> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<MtUserBalance> findByUserId(Long userId);
}
