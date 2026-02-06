package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtSmsTemplate;
import com.fuint.domain.repository.common.MtSmsTemplateRepository;
import com.fuint.infrastructure.persistence.converter.MtSmsTemplateConverter;
import com.fuint.infrastructure.persistence.entity.MtSmsTemplateDO;
import com.fuint.infrastructure.persistence.mapper.MtSmsTemplateMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtSmsTemplateRepositoryImpl implements MtSmsTemplateRepository {
    private final MtSmsTemplateMapper mapper;
    private final MtSmsTemplateConverter converter;
    public MtSmsTemplateRepositoryImpl(MtSmsTemplateMapper mapper, MtSmsTemplateConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtSmsTemplate save(MtSmsTemplate entity) {
        MtSmsTemplateDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtSmsTemplate> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtSmsTemplate> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtSmsTemplateDO>().eq(MtSmsTemplateDO::getId, id)) > 0;
    }
    @Override
    public List<MtSmsTemplate> findByType(String type) {
        return mapper.selectList(new LambdaQueryWrapper<MtSmsTemplateDO>().eq(MtSmsTemplateDO::getType, type))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
