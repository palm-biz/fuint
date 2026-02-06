package com.fuint.infrastructure.persistence.repository.commission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.commission.MtCommissionLog;
import com.fuint.domain.repository.commission.MtCommissionLogRepository;
import com.fuint.infrastructure.persistence.converter.MtCommissionLogConverter;
import com.fuint.infrastructure.persistence.entity.MtCommissionLogDO;
import com.fuint.infrastructure.persistence.mapper.MtCommissionLogMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtCommissionLogRepositoryImpl implements MtCommissionLogRepository {
    private final MtCommissionLogMapper mapper;
    private final MtCommissionLogConverter converter;
    public MtCommissionLogRepositoryImpl(MtCommissionLogMapper mapper, MtCommissionLogConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtCommissionLog save(MtCommissionLog entity) {
        MtCommissionLogDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtCommissionLog> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtCommissionLog> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtCommissionLogDO>().eq(MtCommissionLogDO::getId, id)) > 0;
    }
    @Override
    public List<MtCommissionLog> findByUserId(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<MtCommissionLogDO>().eq(MtCommissionLogDO::getUserId, userId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
