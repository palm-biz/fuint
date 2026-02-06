package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtSetting;
import com.fuint.domain.repository.common.MtSettingRepository;
import com.fuint.infrastructure.persistence.converter.MtSettingConverter;
import com.fuint.infrastructure.persistence.entity.MtSettingDO;
import com.fuint.infrastructure.persistence.mapper.MtSettingMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtSettingRepositoryImpl implements MtSettingRepository {
    private final MtSettingMapper mapper;
    private final MtSettingConverter converter;
    public MtSettingRepositoryImpl(MtSettingMapper mapper, MtSettingConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtSetting save(MtSetting entity) {
        MtSettingDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtSetting> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtSetting> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtSettingDO>().eq(MtSettingDO::getId, id)) > 0;
    }
    @Override
    public List<MtSetting> findByType(String type) {
        return mapper.selectList(new LambdaQueryWrapper<MtSettingDO>().eq(MtSettingDO::getType, type))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
