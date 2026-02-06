package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtMessage;

import java.util.List;
import java.util.Optional;

/**
 * 会员消息仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtMessageRepository {

    MtMessage save(MtMessage mtMessage);

    Optional<MtMessage> findById(Long id);

    List<MtMessage> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtMessage> findByUserId(Long userId);
}
