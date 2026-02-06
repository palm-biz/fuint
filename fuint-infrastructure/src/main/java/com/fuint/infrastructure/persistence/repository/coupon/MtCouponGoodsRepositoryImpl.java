package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtCouponGoods;
import com.fuint.domain.repository.coupon.MtCouponGoodsRepository;
import com.fuint.infrastructure.persistence.converter.MtCouponGoodsConverter;
import com.fuint.infrastructure.persistence.entity.MtCouponGoodsDO;
import com.fuint.infrastructure.persistence.mapper.MtCouponGoodsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 卡券适用商品仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtCouponGoodsRepositoryImpl implements MtCouponGoodsRepository {

    private final MtCouponGoodsMapper mtCouponGoodsMapper;
    private final MtCouponGoodsConverter mtCouponGoodsConverter;

    public MtCouponGoodsRepositoryImpl(MtCouponGoodsMapper mtCouponGoodsMapper, MtCouponGoodsConverter mtCouponGoodsConverter) {
        this.mtCouponGoodsMapper = mtCouponGoodsMapper;
        this.mtCouponGoodsConverter = mtCouponGoodsConverter;
    }

    @Override
    public MtCouponGoods save(MtCouponGoods mtCouponGoods) {
        MtCouponGoodsDO mtCouponGoodsDO = mtCouponGoodsConverter.toDataObject(mtCouponGoods);
        if (mtCouponGoodsDO.getId() == null) {
            mtCouponGoodsMapper.insert(mtCouponGoodsDO);
        } else {
            mtCouponGoodsMapper.updateById(mtCouponGoodsDO);
        }
        return mtCouponGoodsConverter.toDomain(mtCouponGoodsDO);
    }

    @Override
    public Optional<MtCouponGoods> findById(Long id) {
        MtCouponGoodsDO mtCouponGoodsDO = mtCouponGoodsMapper.selectById(id);
        return Optional.ofNullable(mtCouponGoodsConverter.toDomain(mtCouponGoodsDO));
    }

    @Override
    public List<MtCouponGoods> findAll() {
        List<MtCouponGoodsDO> mtCouponGoodsDOS = mtCouponGoodsMapper.selectList(null);
        return mtCouponGoodsDOS.stream()
                .map(mtCouponGoodsConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtCouponGoodsMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtCouponGoodsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponGoodsDO::getId, id);
        return mtCouponGoodsMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtCouponGoods> findByCouponId(Long couponId) {
        LambdaQueryWrapper<MtCouponGoodsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtCouponGoodsDO::getCouponId, couponId);
        List<MtCouponGoodsDO> mtCouponGoodsDOS = mtCouponGoodsMapper.selectList(wrapper);
        return mtCouponGoodsDOS.stream()
                .map(mtCouponGoodsConverter::toDomain)
                .collect(Collectors.toList());
    }
}
