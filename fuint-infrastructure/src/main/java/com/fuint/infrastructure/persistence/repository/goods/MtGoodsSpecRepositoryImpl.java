package com.fuint.infrastructure.persistence.repository.goods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtGoodsSpec;
import com.fuint.domain.repository.goods.MtGoodsSpecRepository;
import com.fuint.infrastructure.persistence.converter.MtGoodsSpecConverter;
import com.fuint.infrastructure.persistence.entity.MtGoodsSpecDO;
import com.fuint.infrastructure.persistence.mapper.MtGoodsSpecMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtGoodsSpecRepositoryImpl implements MtGoodsSpecRepository {
    private final MtGoodsSpecMapper mapper;
    private final MtGoodsSpecConverter converter;
    public MtGoodsSpecRepositoryImpl(MtGoodsSpecMapper mapper, MtGoodsSpecConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtGoodsSpec save(MtGoodsSpec entity) {
        MtGoodsSpecDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtGoodsSpec> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtGoodsSpec> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtGoodsSpecDO>().eq(MtGoodsSpecDO::getId, id)) > 0;
    }
    @Override
    public List<MtGoodsSpec> findByGoodsId(Long goodsId) {
        return mapper.selectList(new LambdaQueryWrapper<MtGoodsSpecDO>().eq(MtGoodsSpecDO::getGoodsId, goodsId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
