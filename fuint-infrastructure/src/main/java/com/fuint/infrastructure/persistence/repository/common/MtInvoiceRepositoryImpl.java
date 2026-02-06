package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtInvoice;
import com.fuint.domain.repository.common.MtInvoiceRepository;
import com.fuint.infrastructure.persistence.converter.MtInvoiceConverter;
import com.fuint.infrastructure.persistence.entity.MtInvoiceDO;
import com.fuint.infrastructure.persistence.mapper.MtInvoiceMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtInvoiceRepositoryImpl implements MtInvoiceRepository {
    private final MtInvoiceMapper mapper;
    private final MtInvoiceConverter converter;
    public MtInvoiceRepositoryImpl(MtInvoiceMapper mapper, MtInvoiceConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtInvoice save(MtInvoice entity) {
        MtInvoiceDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtInvoice> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtInvoice> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtInvoiceDO>().eq(MtInvoiceDO::getId, id)) > 0;
    }
    @Override
    public List<MtInvoice> findByOrderId(Long orderId) {
        return mapper.selectList(new LambdaQueryWrapper<MtInvoiceDO>().eq(MtInvoiceDO::getOrderId, orderId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
