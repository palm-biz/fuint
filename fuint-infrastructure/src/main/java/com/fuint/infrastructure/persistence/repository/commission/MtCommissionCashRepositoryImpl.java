package com.fuint.infrastructure.persistence.repository.commission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.commission.MtCommissionCash;
import com.fuint.domain.repository.commission.MtCommissionCashRepository;
import com.fuint.infrastructure.persistence.converter.MtCommissionCashConverter;
import com.fuint.infrastructure.persistence.entity.MtCommissionCashDO;
import com.fuint.infrastructure.persistence.mapper.MtCommissionCashMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtCommissionCashRepositoryImpl implements MtCommissionCashRepository {
    private final MtCommissionCashMapper mapper;
    private final MtCommissionCashConverter converter;
    public MtCommissionCashRepositoryImpl(MtCommissionCashMapper mapper, MtCommissionCashConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtCommissionCash save(MtCommissionCash entity) {
        MtCommissionCashDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtCommissionCash> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtCommissionCash> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtCommissionCashDO>().eq(MtCommissionCashDO::getId, id)) > 0;
    }
    @Override
    public List<MtCommissionCash> findByUserId(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<MtCommissionCashDO>().eq(MtCommissionCashDO::getUserId, userId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
