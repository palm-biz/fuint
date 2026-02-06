package com.fuint.infrastructure.persistence.repository.store;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.store.MtPrinter;
import com.fuint.domain.repository.store.MtPrinterRepository;
import com.fuint.infrastructure.persistence.converter.MtPrinterConverter;
import com.fuint.infrastructure.persistence.entity.MtPrinterDO;
import com.fuint.infrastructure.persistence.mapper.MtPrinterMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtPrinterRepositoryImpl implements MtPrinterRepository {
    private final MtPrinterMapper mapper;
    private final MtPrinterConverter converter;
    public MtPrinterRepositoryImpl(MtPrinterMapper mapper, MtPrinterConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtPrinter save(MtPrinter entity) {
        MtPrinterDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtPrinter> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtPrinter> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtPrinterDO>().eq(MtPrinterDO::getId, id)) > 0;
    }
    @Override
    public List<MtPrinter> findByStoreId(Long storeId) {
        return mapper.selectList(new LambdaQueryWrapper<MtPrinterDO>().eq(MtPrinterDO::getStoreId, storeId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
