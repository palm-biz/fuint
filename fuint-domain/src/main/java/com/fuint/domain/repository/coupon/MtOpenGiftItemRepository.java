package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtOpenGiftItem;

import java.util.List;
import java.util.Optional;

/**
 * 开卡赠礼明细仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtOpenGiftItemRepository {

    MtOpenGiftItem save(MtOpenGiftItem mtOpenGiftItem);

    Optional<MtOpenGiftItem> findById(Long id);

    List<MtOpenGiftItem> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtOpenGiftItem> findByGiftId(Long giftId);
}
