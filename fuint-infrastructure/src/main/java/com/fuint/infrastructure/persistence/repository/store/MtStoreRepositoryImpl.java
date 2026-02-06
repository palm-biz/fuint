package com.fuint.infrastructure.persistence.repository.store;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.store.MtStore;
import com.fuint.domain.repository.store.MtStoreRepository;
import com.fuint.infrastructure.persistence.converter.MtStoreConverter;
import com.fuint.infrastructure.persistence.entity.MtStoreDO;
import com.fuint.infrastructure.persistence.mapper.MtStoreMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtStoreRepositoryImpl implements MtStoreRepository {
    private final MtStoreMapper mapper;
    private final MtStoreConverter converter;
    public MtStoreRepositoryImpl(MtStoreMapper mapper, MtStoreConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtStore save(MtStore entity) {
        MtStoreDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtStore> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtStore> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtStoreDO>().eq(MtStoreDO::getId, id)) > 0;
    }
    @Override
    public List<MtStore> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtStoreDO>().eq(MtStoreDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
