package com.fuint.infrastructure.persistence.repository.goods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtGoodsSku;
import com.fuint.domain.repository.goods.MtGoodsSkuRepository;
import com.fuint.infrastructure.persistence.converter.MtGoodsSkuConverter;
import com.fuint.infrastructure.persistence.entity.MtGoodsSkuDO;
import com.fuint.infrastructure.persistence.mapper.MtGoodsSkuMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtGoodsSkuRepositoryImpl implements MtGoodsSkuRepository {
    private final MtGoodsSkuMapper mapper;
    private final MtGoodsSkuConverter converter;
    public MtGoodsSkuRepositoryImpl(MtGoodsSkuMapper mapper, MtGoodsSkuConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtGoodsSku save(MtGoodsSku entity) {
        MtGoodsSkuDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtGoodsSku> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtGoodsSku> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtGoodsSkuDO>().eq(MtGoodsSkuDO::getId, id)) > 0;
    }
    @Override
    public List<MtGoodsSku> findByGoodsId(Long goodsId) {
        return mapper.selectList(new LambdaQueryWrapper<MtGoodsSkuDO>().eq(MtGoodsSkuDO::getGoodsId, goodsId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
