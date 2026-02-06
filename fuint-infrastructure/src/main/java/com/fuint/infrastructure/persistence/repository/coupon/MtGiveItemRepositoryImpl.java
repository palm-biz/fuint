package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtGiveItem;
import com.fuint.domain.repository.coupon.MtGiveItemRepository;
import com.fuint.infrastructure.persistence.converter.MtGiveItemConverter;
import com.fuint.infrastructure.persistence.entity.MtGiveItemDO;
import com.fuint.infrastructure.persistence.mapper.MtGiveItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 转赠明细仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtGiveItemRepositoryImpl implements MtGiveItemRepository {

    private final MtGiveItemMapper mtGiveItemMapper;
    private final MtGiveItemConverter mtGiveItemConverter;

    public MtGiveItemRepositoryImpl(MtGiveItemMapper mtGiveItemMapper, MtGiveItemConverter mtGiveItemConverter) {
        this.mtGiveItemMapper = mtGiveItemMapper;
        this.mtGiveItemConverter = mtGiveItemConverter;
    }

    @Override
    public MtGiveItem save(MtGiveItem mtGiveItem) {
        MtGiveItemDO mtGiveItemDO = mtGiveItemConverter.toDataObject(mtGiveItem);
        if (mtGiveItemDO.getId() == null) {
            mtGiveItemMapper.insert(mtGiveItemDO);
        } else {
            mtGiveItemMapper.updateById(mtGiveItemDO);
        }
        return mtGiveItemConverter.toDomain(mtGiveItemDO);
    }

    @Override
    public Optional<MtGiveItem> findById(Long id) {
        MtGiveItemDO mtGiveItemDO = mtGiveItemMapper.selectById(id);
        return Optional.ofNullable(mtGiveItemConverter.toDomain(mtGiveItemDO));
    }

    @Override
    public List<MtGiveItem> findAll() {
        List<MtGiveItemDO> mtGiveItemDOS = mtGiveItemMapper.selectList(null);
        return mtGiveItemDOS.stream()
                .map(mtGiveItemConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtGiveItemMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtGiveItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtGiveItemDO::getId, id);
        return mtGiveItemMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtGiveItem> findByGiveId(Long giveId) {
        LambdaQueryWrapper<MtGiveItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtGiveItemDO::getGiveId, giveId);
        List<MtGiveItemDO> mtGiveItemDOS = mtGiveItemMapper.selectList(wrapper);
        return mtGiveItemDOS.stream()
                .map(mtGiveItemConverter::toDomain)
                .collect(Collectors.toList());
    }
}
