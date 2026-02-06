package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtOrderGoods;
import com.fuint.domain.repository.order.MtOrderGoodsRepository;
import com.fuint.infrastructure.persistence.converter.MtOrderGoodsConverter;
import com.fuint.infrastructure.persistence.entity.MtOrderGoodsDO;
import com.fuint.infrastructure.persistence.mapper.MtOrderGoodsMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtOrderGoodsRepositoryImpl implements MtOrderGoodsRepository {
    private final MtOrderGoodsMapper mtOrderGoodsMapper;
    private final MtOrderGoodsConverter mtOrderGoodsConverter;

    public MtOrderGoodsRepositoryImpl(MtOrderGoodsMapper mtOrderGoodsMapper, MtOrderGoodsConverter mtOrderGoodsConverter) {
        this.mtOrderGoodsMapper = mtOrderGoodsMapper;
        this.mtOrderGoodsConverter = mtOrderGoodsConverter;
    }

    @Override
    public MtOrderGoods save(MtOrderGoods mtOrderGoods) {
        MtOrderGoodsDO mtOrderGoodsDO = mtOrderGoodsConverter.toDataObject(mtOrderGoods);
        if (mtOrderGoodsDO.getId() == null) {
            mtOrderGoodsMapper.insert(mtOrderGoodsDO);
        } else {
            mtOrderGoodsMapper.updateById(mtOrderGoodsDO);
        }
        return mtOrderGoodsConverter.toDomain(mtOrderGoodsDO);
    }

    @Override
    public Optional<MtOrderGoods> findById(Long id) {
        MtOrderGoodsDO mtOrderGoodsDO = mtOrderGoodsMapper.selectById(id);
        return Optional.ofNullable(mtOrderGoodsConverter.toDomain(mtOrderGoodsDO));
    }

    @Override
    public List<MtOrderGoods> findAll() {
        List<MtOrderGoodsDO> mtOrderGoodsDOS = mtOrderGoodsMapper.selectList(null);
        return mtOrderGoodsDOS.stream().map(mtOrderGoodsConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtOrderGoodsMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtOrderGoodsMapper.selectCount(new LambdaQueryWrapper<MtOrderGoodsDO>().eq(MtOrderGoodsDO::getId, id)) > 0;
    }

    @Override
    public List<MtOrderGoods> findByOrderId(Long orderId) {
        List<MtOrderGoodsDO> mtOrderGoodsDOS = mtOrderGoodsMapper.selectList(new LambdaQueryWrapper<MtOrderGoodsDO>().eq(MtOrderGoodsDO::getOrderId, orderId));
        return mtOrderGoodsDOS.stream().map(mtOrderGoodsConverter::toDomain).collect(Collectors.toList());
    }
}
