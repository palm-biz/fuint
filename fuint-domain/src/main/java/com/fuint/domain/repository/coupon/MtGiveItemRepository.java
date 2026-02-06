package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtGiveItem;

import java.util.List;
import java.util.Optional;

/**
 * 转赠明细仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtGiveItemRepository {

    MtGiveItem save(MtGiveItem mtGiveItem);

    Optional<MtGiveItem> findById(Long id);

    List<MtGiveItem> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtGiveItem> findByGiveId(Long giveId);
}
