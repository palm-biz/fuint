package com.fuint.infrastructure.persistence.repository.commission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.commission.MtCommissionRule;
import com.fuint.domain.repository.commission.MtCommissionRuleRepository;
import com.fuint.infrastructure.persistence.converter.MtCommissionRuleConverter;
import com.fuint.infrastructure.persistence.entity.MtCommissionRuleDO;
import com.fuint.infrastructure.persistence.mapper.MtCommissionRuleMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtCommissionRuleRepositoryImpl implements MtCommissionRuleRepository {
    private final MtCommissionRuleMapper mapper;
    private final MtCommissionRuleConverter converter;
    public MtCommissionRuleRepositoryImpl(MtCommissionRuleMapper mapper, MtCommissionRuleConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtCommissionRule save(MtCommissionRule entity) {
        MtCommissionRuleDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtCommissionRule> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtCommissionRule> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtCommissionRuleDO>().eq(MtCommissionRuleDO::getId, id)) > 0;
    }
    @Override
    public List<MtCommissionRule> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtCommissionRuleDO>().eq(MtCommissionRuleDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
