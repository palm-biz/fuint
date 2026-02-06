package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtRegion;
import com.fuint.domain.repository.common.MtRegionRepository;
import com.fuint.infrastructure.persistence.converter.MtRegionConverter;
import com.fuint.infrastructure.persistence.entity.MtRegionDO;
import com.fuint.infrastructure.persistence.mapper.MtRegionMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtRegionRepositoryImpl implements MtRegionRepository {
    private final MtRegionMapper mapper;
    private final MtRegionConverter converter;
    public MtRegionRepositoryImpl(MtRegionMapper mapper, MtRegionConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtRegion save(MtRegion entity) {
        MtRegionDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtRegion> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtRegion> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtRegionDO>().eq(MtRegionDO::getId, id)) > 0;
    }
    @Override
    public List<MtRegion> findByParentId(Long parentId) {
        return mapper.selectList(new LambdaQueryWrapper<MtRegionDO>().eq(MtRegionDO::getParentId, parentId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
