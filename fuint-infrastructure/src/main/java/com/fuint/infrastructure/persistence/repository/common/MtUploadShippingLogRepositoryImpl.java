package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtUploadShippingLog;
import com.fuint.domain.repository.common.MtUploadShippingLogRepository;
import com.fuint.infrastructure.persistence.converter.MtUploadShippingLogConverter;
import com.fuint.infrastructure.persistence.entity.MtUploadShippingLogDO;
import com.fuint.infrastructure.persistence.mapper.MtUploadShippingLogMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtUploadShippingLogRepositoryImpl implements MtUploadShippingLogRepository {
    private final MtUploadShippingLogMapper mapper;
    private final MtUploadShippingLogConverter converter;
    public MtUploadShippingLogRepositoryImpl(MtUploadShippingLogMapper mapper, MtUploadShippingLogConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtUploadShippingLog save(MtUploadShippingLog entity) {
        MtUploadShippingLogDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtUploadShippingLog> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtUploadShippingLog> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtUploadShippingLogDO>().eq(MtUploadShippingLogDO::getId, id)) > 0;
    }
    @Override
    public List<MtUploadShippingLog> findByOrderId(Long orderId) {
        return mapper.selectList(new LambdaQueryWrapper<MtUploadShippingLogDO>().eq(MtUploadShippingLogDO::getOrderId, orderId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
