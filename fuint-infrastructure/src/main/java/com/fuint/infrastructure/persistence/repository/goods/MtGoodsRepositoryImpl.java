package com.fuint.infrastructure.persistence.repository.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtGoods;
import com.fuint.domain.repository.goods.MtGoodsRepository;
import com.fuint.infrastructure.persistence.converter.MtGoodsConverter;
import com.fuint.infrastructure.persistence.entity.MtGoodsDO;
import com.fuint.infrastructure.persistence.mapper.MtGoodsMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtGoodsRepositoryImpl implements MtGoodsRepository {
    private final MtGoodsMapper mtGoodsMapper;
    private final MtGoodsConverter mtGoodsConverter;

    public MtGoodsRepositoryImpl(MtGoodsMapper mtGoodsMapper, MtGoodsConverter mtGoodsConverter) {
        this.mtGoodsMapper = mtGoodsMapper;
        this.mtGoodsConverter = mtGoodsConverter;
    }

    @Override
    public MtGoods save(MtGoods mtGoods) {
        MtGoodsDO mtGoodsDO = mtGoodsConverter.toDataObject(mtGoods);
        if (mtGoodsDO.getId() == null) {
            mtGoodsMapper.insert(mtGoodsDO);
        } else {
            mtGoodsMapper.updateById(mtGoodsDO);
        }
        return mtGoodsConverter.toDomain(mtGoodsDO);
    }

    @Override
    public Optional<MtGoods> findById(Long id) {
        MtGoodsDO mtGoodsDO = mtGoodsMapper.selectById(id);
        return Optional.ofNullable(mtGoodsConverter.toDomain(mtGoodsDO));
    }

    @Override
    public List<MtGoods> findAll() {
        List<MtGoodsDO> mtGoodsDOS = mtGoodsMapper.selectList(null);
        return mtGoodsDOS.stream().map(mtGoodsConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtGoodsMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtGoodsMapper.selectCount(new LambdaQueryWrapper<MtGoodsDO>().eq(MtGoodsDO::getId, id)) > 0;
    }

    @Override
    public List<MtGoods> findByStatus(String status) {
        List<MtGoodsDO> mtGoodsDOS = mtGoodsMapper.selectList(new LambdaQueryWrapper<MtGoodsDO>().eq(MtGoodsDO::getStatus, status));
        return mtGoodsDOS.stream().map(mtGoodsConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MtGoods> findByCateId(Long cateId) {
        List<MtGoodsDO> mtGoodsDOS = mtGoodsMapper.selectList(new LambdaQueryWrapper<MtGoodsDO>().eq(MtGoodsDO::getCateId, cateId));
        return mtGoodsDOS.stream().map(mtGoodsConverter::toDomain).collect(Collectors.toList());
    }
}
