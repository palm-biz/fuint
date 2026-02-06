package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtVerifyCode;

import java.util.List;
import java.util.Optional;

/**
 * 验证码仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtVerifyCodeRepository {

    MtVerifyCode save(MtVerifyCode mtVerifyCode);

    Optional<MtVerifyCode> findById(Long id);

    List<MtVerifyCode> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<MtVerifyCode> findByMobile(String mobile);
}
