package com.fuint.domain.repository.common;
import com.fuint.domain.model.common.MtArticle;
import java.util.List;
import java.util.Optional;
public interface MtArticleRepository {
    MtArticle save(MtArticle entity);
    Optional<MtArticle> findById(Long id);
    List<MtArticle> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MtArticle> findByStatus(String status);
}
