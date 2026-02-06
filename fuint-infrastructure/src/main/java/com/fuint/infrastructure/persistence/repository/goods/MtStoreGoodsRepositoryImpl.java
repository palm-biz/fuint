package com.fuint.infrastructure.persistence.repository.goods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtStoreGoods;
import com.fuint.domain.repository.goods.MtStoreGoodsRepository;
import com.fuint.infrastructure.persistence.converter.MtStoreGoodsConverter;
import com.fuint.infrastructure.persistence.entity.MtStoreGoodsDO;
import com.fuint.infrastructure.persistence.mapper.MtStoreGoodsMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtStoreGoodsRepositoryImpl implements MtStoreGoodsRepository {
    private final MtStoreGoodsMapper mapper;
    private final MtStoreGoodsConverter converter;
    public MtStoreGoodsRepositoryImpl(MtStoreGoodsMapper mapper, MtStoreGoodsConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtStoreGoods save(MtStoreGoods entity) {
        MtStoreGoodsDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtStoreGoods> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtStoreGoods> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtStoreGoodsDO>().eq(MtStoreGoodsDO::getId, id)) > 0;
    }
    @Override
    public List<MtStoreGoods> findByStoreId(Long storeId) {
        return mapper.selectList(new LambdaQueryWrapper<MtStoreGoodsDO>().eq(MtStoreGoodsDO::getStoreId, storeId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
