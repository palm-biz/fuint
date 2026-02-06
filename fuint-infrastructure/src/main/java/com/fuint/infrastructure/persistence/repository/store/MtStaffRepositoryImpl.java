package com.fuint.infrastructure.persistence.repository.store;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.store.MtStaff;
import com.fuint.domain.repository.store.MtStaffRepository;
import com.fuint.infrastructure.persistence.converter.MtStaffConverter;
import com.fuint.infrastructure.persistence.entity.MtStaffDO;
import com.fuint.infrastructure.persistence.mapper.MtStaffMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtStaffRepositoryImpl implements MtStaffRepository {
    private final MtStaffMapper mapper;
    private final MtStaffConverter converter;
    public MtStaffRepositoryImpl(MtStaffMapper mapper, MtStaffConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtStaff save(MtStaff entity) {
        MtStaffDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtStaff> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtStaff> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtStaffDO>().eq(MtStaffDO::getId, id)) > 0;
    }
    @Override
    public List<MtStaff> findByStoreId(Long storeId) {
        return mapper.selectList(new LambdaQueryWrapper<MtStaffDO>().eq(MtStaffDO::getStoreId, storeId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
