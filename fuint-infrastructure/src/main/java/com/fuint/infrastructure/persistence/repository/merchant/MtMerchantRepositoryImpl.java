package com.fuint.infrastructure.persistence.repository.merchant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.merchant.MtMerchant;
import com.fuint.domain.repository.merchant.MtMerchantRepository;
import com.fuint.infrastructure.persistence.converter.MtMerchantConverter;
import com.fuint.infrastructure.persistence.entity.MtMerchantDO;
import com.fuint.infrastructure.persistence.mapper.MtMerchantMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtMerchantRepositoryImpl implements MtMerchantRepository {
    private final MtMerchantMapper mapper;
    private final MtMerchantConverter converter;
    public MtMerchantRepositoryImpl(MtMerchantMapper mapper, MtMerchantConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtMerchant save(MtMerchant entity) {
        MtMerchantDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtMerchant> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtMerchant> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtMerchantDO>().eq(MtMerchantDO::getId, id)) > 0;
    }
    @Override
    public List<MtMerchant> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtMerchantDO>().eq(MtMerchantDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
