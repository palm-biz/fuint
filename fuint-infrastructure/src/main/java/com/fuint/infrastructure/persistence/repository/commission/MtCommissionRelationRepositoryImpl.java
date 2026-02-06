package com.fuint.infrastructure.persistence.repository.commission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.commission.MtCommissionRelation;
import com.fuint.domain.repository.commission.MtCommissionRelationRepository;
import com.fuint.infrastructure.persistence.converter.MtCommissionRelationConverter;
import com.fuint.infrastructure.persistence.entity.MtCommissionRelationDO;
import com.fuint.infrastructure.persistence.mapper.MtCommissionRelationMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtCommissionRelationRepositoryImpl implements MtCommissionRelationRepository {
    private final MtCommissionRelationMapper mapper;
    private final MtCommissionRelationConverter converter;
    public MtCommissionRelationRepositoryImpl(MtCommissionRelationMapper mapper, MtCommissionRelationConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtCommissionRelation save(MtCommissionRelation entity) {
        MtCommissionRelationDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtCommissionRelation> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtCommissionRelation> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtCommissionRelationDO>().eq(MtCommissionRelationDO::getId, id)) > 0;
    }
    @Override
    public List<MtCommissionRelation> findByUserId(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<MtCommissionRelationDO>().eq(MtCommissionRelationDO::getUserId, userId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
