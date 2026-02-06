package com.fuint.infrastructure.persistence.repository.commission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.commission.MtCommissionRuleItem;
import com.fuint.domain.repository.commission.MtCommissionRuleItemRepository;
import com.fuint.infrastructure.persistence.converter.MtCommissionRuleItemConverter;
import com.fuint.infrastructure.persistence.entity.MtCommissionRuleItemDO;
import com.fuint.infrastructure.persistence.mapper.MtCommissionRuleItemMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtCommissionRuleItemRepositoryImpl implements MtCommissionRuleItemRepository {
    private final MtCommissionRuleItemMapper mapper;
    private final MtCommissionRuleItemConverter converter;
    public MtCommissionRuleItemRepositoryImpl(MtCommissionRuleItemMapper mapper, MtCommissionRuleItemConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtCommissionRuleItem save(MtCommissionRuleItem entity) {
        MtCommissionRuleItemDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtCommissionRuleItem> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtCommissionRuleItem> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtCommissionRuleItemDO>().eq(MtCommissionRuleItemDO::getId, id)) > 0;
    }
    @Override
    public List<MtCommissionRuleItem> findByRuleId(Long ruleId) {
        return mapper.selectList(new LambdaQueryWrapper<MtCommissionRuleItemDO>().eq(MtCommissionRuleItemDO::getRuleId, ruleId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
