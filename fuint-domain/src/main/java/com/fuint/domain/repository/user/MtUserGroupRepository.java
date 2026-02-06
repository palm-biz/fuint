package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtUserGroup;

import java.util.List;
import java.util.Optional;

/**
 * 会员分组仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtUserGroupRepository {

    MtUserGroup save(MtUserGroup mtUserGroup);

    Optional<MtUserGroup> findById(Long id);

    List<MtUserGroup> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtUserGroup> findByStatus(String status);
}
