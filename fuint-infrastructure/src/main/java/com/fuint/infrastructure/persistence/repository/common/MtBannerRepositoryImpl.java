package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtBanner;
import com.fuint.domain.repository.common.MtBannerRepository;
import com.fuint.infrastructure.persistence.converter.MtBannerConverter;
import com.fuint.infrastructure.persistence.entity.MtBannerDO;
import com.fuint.infrastructure.persistence.mapper.MtBannerMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtBannerRepositoryImpl implements MtBannerRepository {
    private final MtBannerMapper mapper;
    private final MtBannerConverter converter;
    public MtBannerRepositoryImpl(MtBannerMapper mapper, MtBannerConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtBanner save(MtBanner entity) {
        MtBannerDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtBanner> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtBanner> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtBannerDO>().eq(MtBannerDO::getId, id)) > 0;
    }
    @Override
    public List<MtBanner> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtBannerDO>().eq(MtBannerDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
