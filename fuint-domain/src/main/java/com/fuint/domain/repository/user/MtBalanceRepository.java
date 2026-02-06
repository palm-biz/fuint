package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtBalance;

import java.util.List;
import java.util.Optional;

/**
 * 会员余额明细仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtBalanceRepository {

    MtBalance save(MtBalance mtBalance);

    Optional<MtBalance> findById(Long id);

    List<MtBalance> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtBalance> findByUserId(Long userId);
}
