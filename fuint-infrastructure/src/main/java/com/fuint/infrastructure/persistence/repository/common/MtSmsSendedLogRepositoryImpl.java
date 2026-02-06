package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtSmsSendedLog;
import com.fuint.domain.repository.common.MtSmsSendedLogRepository;
import com.fuint.infrastructure.persistence.converter.MtSmsSendedLogConverter;
import com.fuint.infrastructure.persistence.entity.MtSmsSendedLogDO;
import com.fuint.infrastructure.persistence.mapper.MtSmsSendedLogMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtSmsSendedLogRepositoryImpl implements MtSmsSendedLogRepository {
    private final MtSmsSendedLogMapper mapper;
    private final MtSmsSendedLogConverter converter;
    public MtSmsSendedLogRepositoryImpl(MtSmsSendedLogMapper mapper, MtSmsSendedLogConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtSmsSendedLog save(MtSmsSendedLog entity) {
        MtSmsSendedLogDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtSmsSendedLog> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtSmsSendedLog> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtSmsSendedLogDO>().eq(MtSmsSendedLogDO::getId, id)) > 0;
    }
    @Override
    public List<MtSmsSendedLog> findByMobile(String mobile) {
        return mapper.selectList(new LambdaQueryWrapper<MtSmsSendedLogDO>().eq(MtSmsSendedLogDO::getMobile, mobile))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
