package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtOpenGiftItem;
import com.fuint.domain.repository.coupon.MtOpenGiftItemRepository;
import com.fuint.infrastructure.persistence.converter.MtOpenGiftItemConverter;
import com.fuint.infrastructure.persistence.entity.MtOpenGiftItemDO;
import com.fuint.infrastructure.persistence.mapper.MtOpenGiftItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 开卡赠礼明细仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtOpenGiftItemRepositoryImpl implements MtOpenGiftItemRepository {

    private final MtOpenGiftItemMapper mtOpenGiftItemMapper;
    private final MtOpenGiftItemConverter mtOpenGiftItemConverter;

    public MtOpenGiftItemRepositoryImpl(MtOpenGiftItemMapper mtOpenGiftItemMapper, MtOpenGiftItemConverter mtOpenGiftItemConverter) {
        this.mtOpenGiftItemMapper = mtOpenGiftItemMapper;
        this.mtOpenGiftItemConverter = mtOpenGiftItemConverter;
    }

    @Override
    public MtOpenGiftItem save(MtOpenGiftItem mtOpenGiftItem) {
        MtOpenGiftItemDO mtOpenGiftItemDO = mtOpenGiftItemConverter.toDataObject(mtOpenGiftItem);
        if (mtOpenGiftItemDO.getId() == null) {
            mtOpenGiftItemMapper.insert(mtOpenGiftItemDO);
        } else {
            mtOpenGiftItemMapper.updateById(mtOpenGiftItemDO);
        }
        return mtOpenGiftItemConverter.toDomain(mtOpenGiftItemDO);
    }

    @Override
    public Optional<MtOpenGiftItem> findById(Long id) {
        MtOpenGiftItemDO mtOpenGiftItemDO = mtOpenGiftItemMapper.selectById(id);
        return Optional.ofNullable(mtOpenGiftItemConverter.toDomain(mtOpenGiftItemDO));
    }

    @Override
    public List<MtOpenGiftItem> findAll() {
        List<MtOpenGiftItemDO> mtOpenGiftItemDOS = mtOpenGiftItemMapper.selectList(null);
        return mtOpenGiftItemDOS.stream()
                .map(mtOpenGiftItemConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtOpenGiftItemMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtOpenGiftItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtOpenGiftItemDO::getId, id);
        return mtOpenGiftItemMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtOpenGiftItem> findByGiftId(Long giftId) {
        LambdaQueryWrapper<MtOpenGiftItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtOpenGiftItemDO::getGiftId, giftId);
        List<MtOpenGiftItemDO> mtOpenGiftItemDOS = mtOpenGiftItemMapper.selectList(wrapper);
        return mtOpenGiftItemDOS.stream()
                .map(mtOpenGiftItemConverter::toDomain)
                .collect(Collectors.toList());
    }
}
