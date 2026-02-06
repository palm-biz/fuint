package com.fuint.infrastructure.persistence.repository.goods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtStock;
import com.fuint.domain.repository.goods.MtStockRepository;
import com.fuint.infrastructure.persistence.converter.MtStockConverter;
import com.fuint.infrastructure.persistence.entity.MtStockDO;
import com.fuint.infrastructure.persistence.mapper.MtStockMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtStockRepositoryImpl implements MtStockRepository {
    private final MtStockMapper mapper;
    private final MtStockConverter converter;
    public MtStockRepositoryImpl(MtStockMapper mapper, MtStockConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtStock save(MtStock entity) {
        MtStockDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtStock> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtStock> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtStockDO>().eq(MtStockDO::getId, id)) > 0;
    }
    @Override
    public List<MtStock> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtStockDO>().eq(MtStockDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
