package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtAddress;

import java.util.List;
import java.util.Optional;

/**
 * 会员地址仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtAddressRepository {

    /**
     * 保存会员地址
     *
     * @param mtAddress 会员地址实体
     * @return 保存后的会员地址
     */
    MtAddress save(MtAddress mtAddress);

    /**
     * 根据 ID 查询会员地址
     *
     * @param id 会员地址 ID
     * @return 会员地址实体
     */
    Optional<MtAddress> findById(Long id);

    /**
     * 查询所有会员地址
     *
     * @return 会员地址列表
     */
    List<MtAddress> findAll();

    /**
     * 删除会员地址
     *
     * @param id 会员地址 ID
     */
    void deleteById(Long id);

    /**
     * 会员地址是否存在
     *
     * @param id 会员地址 ID
     * @return true=存在
     */
    boolean existsById(Long id);

    /**
     * 根据会员ID查询地址列表
     *
     * @param userId 会员ID
     * @return 地址列表
     */
    List<MtAddress> findByUserId(Long userId);

    /**
     * 查询会员的默认地址
     *
     * @param userId 会员ID
     * @return 默认地址
     */
    Optional<MtAddress> findDefaultByUserId(Long userId);
}
