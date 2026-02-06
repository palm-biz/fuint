package com.fuint.infrastructure.persistence.repository.goods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.goods.MtGoodsCate;
import com.fuint.domain.repository.goods.MtGoodsCateRepository;
import com.fuint.infrastructure.persistence.converter.MtGoodsCateConverter;
import com.fuint.infrastructure.persistence.entity.MtGoodsCateDO;
import com.fuint.infrastructure.persistence.mapper.MtGoodsCateMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtGoodsCateRepositoryImpl implements MtGoodsCateRepository {
    private final MtGoodsCateMapper mapper;
    private final MtGoodsCateConverter converter;
    public MtGoodsCateRepositoryImpl(MtGoodsCateMapper mapper, MtGoodsCateConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtGoodsCate save(MtGoodsCate entity) {
        MtGoodsCateDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtGoodsCate> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtGoodsCate> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtGoodsCateDO>().eq(MtGoodsCateDO::getId, id)) > 0;
    }
    @Override
    public List<MtGoodsCate> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtGoodsCateDO>().eq(MtGoodsCateDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
