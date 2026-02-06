package com.fuint.infrastructure.persistence.repository.booking;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.booking.MtBook;
import com.fuint.domain.repository.booking.MtBookRepository;
import com.fuint.infrastructure.persistence.converter.MtBookConverter;
import com.fuint.infrastructure.persistence.entity.MtBookDO;
import com.fuint.infrastructure.persistence.mapper.MtBookMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtBookRepositoryImpl implements MtBookRepository {
    private final MtBookMapper mapper;
    private final MtBookConverter converter;
    public MtBookRepositoryImpl(MtBookMapper mapper, MtBookConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtBook save(MtBook entity) {
        MtBookDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtBook> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtBook> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtBookDO>().eq(MtBookDO::getId, id)) > 0;
    }
    @Override
    public List<MtBook> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtBookDO>().eq(MtBookDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
