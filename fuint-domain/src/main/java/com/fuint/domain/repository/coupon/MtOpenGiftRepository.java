package com.fuint.domain.repository.coupon;

import com.fuint.domain.model.coupon.MtOpenGift;

import java.util.List;
import java.util.Optional;

/**
 * 开卡赠礼仓储接口 (领域层定义,基础设施层实现)
 *
 * @author fuint
 * @since 2.0.0
 */
public interface MtOpenGiftRepository {

    MtOpenGift save(MtOpenGift mtOpenGift);

    Optional<MtOpenGift> findById(Long id);

    List<MtOpenGift> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<MtOpenGift> findByStatus(String status);

    Optional<MtOpenGift> findByGradeId(Long gradeId);
}
