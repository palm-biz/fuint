package com.fuint.domain.repository.user;

import com.fuint.domain.model.user.MtUser;

import java.util.List;
import java.util.Optional;

/**
 * 会员仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtUserRepository {

    /**
     * 保存会员
     *
     * @param mtUser 会员实体
     * @return 保存后的会员
     */
    MtUser save(MtUser mtUser);

    /**
     * 根据 ID 查询会员
     *
     * @param id 会员 ID
     * @return 会员实体
     */
    Optional<MtUser> findById(Long id);

    /**
     * 查询所有会员
     *
     * @return 会员列表
     */
    List<MtUser> findAll();

    /**
     * 删除会员
     *
     * @param id 会员 ID
     */
    void deleteById(Long id);

    /**
     * 会员是否存在
     *
     * @param id 会员 ID
     * @return true=存在
     */
    boolean existsById(Long id);

    /**
     * 根据手机号查询会员
     *
     * @param mobile 手机号
     * @return 会员实体
     */
    Optional<MtUser> findByMobile(String mobile);

    /**
     * 根据openId查询会员
     *
     * @param openId openId
     * @return 会员实体
     */
    Optional<MtUser> findByOpenId(String openId);

    /**
     * 根据状态查询会员列表
     *
     * @param status 状态
     * @return 会员列表
     */
    List<MtUser> findByStatus(String status);
}
