package com.fuint.infrastructure.persistence.repository.common;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.common.MtArticle;
import com.fuint.domain.repository.common.MtArticleRepository;
import com.fuint.infrastructure.persistence.converter.MtArticleConverter;
import com.fuint.infrastructure.persistence.entity.MtArticleDO;
import com.fuint.infrastructure.persistence.mapper.MtArticleMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class MtArticleRepositoryImpl implements MtArticleRepository {
    private final MtArticleMapper mapper;
    private final MtArticleConverter converter;
    public MtArticleRepositoryImpl(MtArticleMapper mapper, MtArticleConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }
    @Override
    public MtArticle save(MtArticle entity) {
        MtArticleDO dataObject = converter.toDataObject(entity);
        if (dataObject.getId() == null) { mapper.insert(dataObject); } else { mapper.updateById(dataObject); }
        return converter.toDomain(dataObject);
    }
    @Override
    public Optional<MtArticle> findById(Long id) {
        return Optional.ofNullable(converter.toDomain(mapper.selectById(id)));
    }
    @Override
    public List<MtArticle> findAll() {
        return mapper.selectList(null).stream().map(converter::toDomain).collect(Collectors.toList());
    }
    @Override
    public void deleteById(Long id) { mapper.deleteById(id); }
    @Override
    public boolean existsById(Long id) {
        return mapper.selectCount(new LambdaQueryWrapper<MtArticleDO>().eq(MtArticleDO::getId, id)) > 0;
    }
    @Override
    public List<MtArticle> findByStatus(String status) {
        return mapper.selectList(new LambdaQueryWrapper<MtArticleDO>().eq(MtArticleDO::getStatus, status))
            .stream().map(converter::toDomain).collect(Collectors.toList());
    }
}
