package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtCoupon;
import com.fuint.domain.repository.coupon.MtCouponRepository;
import com.fuint.infrastructure.persistence.converter.MtCouponConverter;
import com.fuint.infrastructure.persistence.entity.MtCouponDO;
import com.fuint.infrastructure.persistence.mapper.MtCouponMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 卡券仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtCouponRepositoryImpl implements MtCouponRepository {

    private final MtCouponMapper mtCouponMapper;
    private final MtCouponConverter mtCouponConverter;

    public MtCouponRepositoryImpl(MtCouponMapper mtCouponMapper, MtCouponConverter mtCouponConverter) {
        this.mtCouponMapper = mtCouponMapper;
        this.mtCouponConverter = mtCouponConverter;
    }

    @Override
    public MtCoupon save(MtCoupon mtCoupon) {
        MtCouponDO mtCouponDO = mtCouponConverter.toDataObject(mtCoupon);
        if (mtCouponDO.getId() == null) {
            mtCouponMapper.insert(mtCouponDO);
        } else {
            mtCouponMapper.updateById(mtCouponDO);
        }
        return mtCouponConverter.toDomain(mtCouponDO);
    }

    @Override
    public Optional<MtCoupon> findById(Long id) {
        MtCouponDO mtCouponDO = mtCouponMapper.selectById(id);
        return Optional.ofNullable(mtCouponConverter.toDomain(mtCouponDO));
    }

    @Override
    public List<MtCoupon> findAll() {
        List<MtCouponDO> mtCouponDOS = mtCouponMapper.selectList(null);
        return mtCouponDOS.stream()
                .map(mtCouponConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtCouponMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtCouponDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponDO::getId, id);
        return mtCouponMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtCoupon> findByStatus(String status) {
        LambdaQueryWrapper<MtCouponDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponDO::getStatus, status);
        List<MtCouponDO> mtCouponDOS = mtCouponMapper.selectList(wrapper);
        return mtCouponDOS.stream()
                .map(mtCouponConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MtCoupon> findByGroupId(Long groupId) {
        LambdaQueryWrapper<MtCouponDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponDO::getGroupId, groupId);
        List<MtCouponDO> mtCouponDOS = mtCouponMapper.selectList(wrapper);
        return mtCouponDOS.stream()
                .map(mtCouponConverter::toDomain)
                .collect(Collectors.toList());
    }
}
