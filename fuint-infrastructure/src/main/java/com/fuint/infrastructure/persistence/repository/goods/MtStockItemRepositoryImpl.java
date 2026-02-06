package com.fuint.infrastructure.persistence.repository.goods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtStockItem;
import com.fuint.domain.repository.goods.MtStockItemRepository;
import com.fuint.infrastructure.persistence.converter.MtStockItemConverter;
import com.fuint.infrastructure.persistence.entity.MtStockItemDO;
import com.fuint.infrastructure.persistence.mapper.MtStockItemMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtStockItemRepositoryImpl implements MtStockItemRepository {
    private final MtStockItemMapper mapper;
    private final MtStockItemConverter converter;
    public MtStockItemRepositoryImpl(MtStockItemMapper mapper, MtStockItemConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtStockItem save(MtStockItem entity) {
        MtStockItemDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtStockItem> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtStockItem> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtStockItemDO>().eq(MtStockItemDO::getId, id)) > 0;
    }
    @Override
    public List<MtStockItem> findByStockId(Long stockId) {
        return mapper.selectList(new LambdaQueryWrapper<MtStockItemDO>().eq(MtStockItemDO::getStockId, stockId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
