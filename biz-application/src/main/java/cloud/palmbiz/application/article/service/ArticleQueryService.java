package cloud.palmbiz.application.article.service;

import cloud.palmbiz.common.article.dto.ArticleDto;
import cloud.palmbiz.common.article.dto.ArticlePage;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleQueryService {

    private final ArticleService articleService;

    public PaginationResponse<ArticleDto> queryArticleListByPagination(ArticlePage articlePage) {
        return articleService.queryArticleListByPagination(articlePage);
    }

    public MtArticle queryArticleById(Integer id) {
        return articleService.queryArticleById(id);
    }

    public ArticleDto getArticleDetail(Integer id) {
        return articleService.getArticleDetail(id);
    }

    public List<MtArticle> queryArticleListByParams(Map<String, Object> params) throws BusinessCheckException {
        return articleService.queryArticleListByParams(params);
    }
}
