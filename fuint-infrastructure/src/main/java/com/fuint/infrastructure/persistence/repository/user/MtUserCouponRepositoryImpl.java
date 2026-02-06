package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtUserCoupon;
import com.fuint.domain.repository.user.MtUserCouponRepository;
import com.fuint.infrastructure.persistence.converter.MtUserCouponConverter;
import com.fuint.infrastructure.persistence.entity.MtUserCouponDO;
import com.fuint.infrastructure.persistence.mapper.MtUserCouponMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员卡券仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtUserCouponRepositoryImpl implements MtUserCouponRepository {

    private final MtUserCouponMapper mtUserCouponMapper;
    private final MtUserCouponConverter mtUserCouponConverter;

    public MtUserCouponRepositoryImpl(MtUserCouponMapper mtUserCouponMapper, MtUserCouponConverter mtUserCouponConverter) {
        this.mtUserCouponMapper = mtUserCouponMapper;
        this.mtUserCouponConverter = mtUserCouponConverter;
    }

    @Override
    public MtUserCoupon save(MtUserCoupon mtUserCoupon) {
        MtUserCouponDO mtUserCouponDO = mtUserCouponConverter.toDataObject(mtUserCoupon);
        if (mtUserCouponDO.getId() == null) {
            mtUserCouponMapper.insert(mtUserCouponDO);
        } else {
            mtUserCouponMapper.updateById(mtUserCouponDO);
        }
        return mtUserCouponConverter.toDomain(mtUserCouponDO);
    }

    @Override
    public Optional<MtUserCoupon> findById(Long id) {
        MtUserCouponDO mtUserCouponDO = mtUserCouponMapper.selectById(id);
        return Optional.ofNullable(mtUserCouponConverter.toDomain(mtUserCouponDO));
    }

    @Override
    public List<MtUserCoupon> findAll() {
        List<MtUserCouponDO> mtUserCouponDOS = mtUserCouponMapper.selectList(null);
        return mtUserCouponDOS.stream()
                .map(mtUserCouponConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtUserCouponMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtUserCouponDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserCouponDO::getId, id);
        return mtUserCouponMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtUserCoupon> findByUserId(Long userId) {
        LambdaQueryWrapper<MtUserCouponDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserCouponDO::getUserId, userId);
        List<MtUserCouponDO> mtUserCouponDOS = mtUserCouponMapper.selectList(wrapper);
        return mtUserCouponDOS.stream()
                .map(mtUserCouponConverter::toDomain)
                .collect(Collectors.toList());
    }
}
