package com.fuint.infrastructure.persistence.repository.booking;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.booking.MtBookItem;
import com.fuint.domain.repository.booking.MtBookItemRepository;
import com.fuint.infrastructure.persistence.converter.MtBookItemConverter;
import com.fuint.infrastructure.persistence.entity.MtBookItemDO;
import com.fuint.infrastructure.persistence.mapper.MtBookItemMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtBookItemRepositoryImpl implements MtBookItemRepository {
    private final MtBookItemMapper mapper;
    private final MtBookItemConverter converter;
    public MtBookItemRepositoryImpl(MtBookItemMapper mapper, MtBookItemConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtBookItem save(MtBookItem entity) {
        MtBookItemDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtBookItem> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtBookItem> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtBookItemDO>().eq(MtBookItemDO::getId, id)) > 0;
    }
    @Override
    public List<MtBookItem> findByBookId(Long bookId) {
        return mapper.selectList(new LambdaQueryWrapper<MtBookItemDO>().eq(MtBookItemDO::getBookId, bookId))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
