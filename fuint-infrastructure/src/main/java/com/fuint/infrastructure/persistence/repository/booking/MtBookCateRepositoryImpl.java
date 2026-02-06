package com.fuint.infrastructure.persistence.repository.booking;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.booking.MtBookCate;
import com.fuint.domain.repository.booking.MtBookCateRepository;
import com.fuint.infrastructure.persistence.converter.MtBookCateConverter;
import com.fuint.infrastructure.persistence.entity.MtBookCateDO;
import com.fuint.infrastructure.persistence.mapper.MtBookCateMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtBookCateRepositoryImpl implements MtBookCateRepository {
    private final MtBookCateMapper mapper;
    private final MtBookCateConverter converter;
    public MtBookCateRepositoryImpl(MtBookCateMapper mapper, MtBookCateConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtBookCate save(MtBookCate entity) {
        MtBookCateDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtBookCate> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtBookCate> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtBookCateDO>().eq(MtBookCateDO::getId, id)) > 0;
    }
    @Override
    public List<MtBookCate> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtBookCateDO>().eq(MtBookCateDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
