package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtUserAction;

import java.util.List;
import java.util.Optional;

/**
 * 会员行为仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtUserActionRepository {

    MtUserAction save(MtUserAction mtUserAction);

    Optional<MtUserAction> findById(Long id);

    List<MtUserAction> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtUserAction> findByUserId(Long userId);
}
