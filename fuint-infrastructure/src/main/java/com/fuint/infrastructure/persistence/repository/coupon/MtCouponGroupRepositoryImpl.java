package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtCouponGroup;
import com.fuint.domain.repository.coupon.MtCouponGroupRepository;
import com.fuint.infrastructure.persistence.converter.MtCouponGroupConverter;
import com.fuint.infrastructure.persistence.entity.MtCouponGroupDO;
import com.fuint.infrastructure.persistence.mapper.MtCouponGroupMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 卡券分组仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtCouponGroupRepositoryImpl implements MtCouponGroupRepository {

    private final MtCouponGroupMapper mtCouponGroupMapper;
    private final MtCouponGroupConverter mtCouponGroupConverter;

    public MtCouponGroupRepositoryImpl(MtCouponGroupMapper mtCouponGroupMapper, MtCouponGroupConverter mtCouponGroupConverter) {
        this.mtCouponGroupMapper = mtCouponGroupMapper;
        this.mtCouponGroupConverter = mtCouponGroupConverter;
    }

    @Override
    public MtCouponGroup save(MtCouponGroup mtCouponGroup) {
        MtCouponGroupDO mtCouponGroupDO = mtCouponGroupConverter.toDataObject(mtCouponGroup);
        if (mtCouponGroupDO.getId() == null) {
            mtCouponGroupMapper.insert(mtCouponGroupDO);
        } else {
            mtCouponGroupMapper.updateById(mtCouponGroupDO);
        }
        return mtCouponGroupConverter.toDomain(mtCouponGroupDO);
    }

    @Override
    public Optional<MtCouponGroup> findById(Long id) {
        MtCouponGroupDO mtCouponGroupDO = mtCouponGroupMapper.selectById(id);
        return Optional.ofNullable(mtCouponGroupConverter.toDomain(mtCouponGroupDO));
    }

    @Override
    public List<MtCouponGroup> findAll() {
        List<MtCouponGroupDO> mtCouponGroupDOS = mtCouponGroupMapper.selectList(null);
        return mtCouponGroupDOS.stream()
                .map(mtCouponGroupConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtCouponGroupMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtCouponGroupDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponGroupDO::getId, id);
        return mtCouponGroupMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtCouponGroup> findByStatus(String status) {
        LambdaQueryWrapper<MtCouponGroupDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponGroupDO::getStatus, status);
        List<MtCouponGroupDO> mtCouponGroupDOS = mtCouponGroupMapper.selectList(wrapper);
        return mtCouponGroupDOS.stream()
                .map(mtCouponGroupConverter::toDomain)
                .collect(Collectors.toList());
    }
}
