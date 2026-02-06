package com.fuint.domain.repository;

import com.fuint.domain.model.merchant.Merchant;

import java.util.List;
import java.util.Optional;

/**
 * 商户仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MerchantRepository {

    /**
     * 保存商户
     *
     * @param merchant 商户实体
     * @return 保存后的商户
     */
    Merchant save(Merchant merchant);

    /**
     * 根据 ID 查询商户
     *
     * @param id 商户 ID
     * @return 商户实体
     */
    Optional<Merchant> findById(Long id);

    /**
     * 根据租户 ID 查询商户
     *
     * @param tenantId 租户 ID
     * @return 商户实体
     */
    Optional<Merchant> findByTenantId(Long tenantId);

    /**
     * 查询所有激活的商户
     *
     * @return 商户列表
     */
    List<Merchant> findAllActive();

    /**
     * 删除商户
     *
     * @param id 商户 ID
     */
    void deleteById(Long id);

    /**
     * 商户是否存在
     *
     * @param id 商户 ID
     * @return true=存在
     */
    boolean existsById(Long id);
}
