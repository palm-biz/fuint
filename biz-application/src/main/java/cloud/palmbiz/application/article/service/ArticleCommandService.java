package cloud.palmbiz.application.article.service;

import cloud.palmbiz.common.article.dto.ArticleDto;
import cloud.palmbiz.common.service.ArticleService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleCommandService {

    private final ArticleService articleService;

    @Transactional(rollbackFor = Exception.class)
    public MtArticle addArticle(ArticleDto articleDto) throws BusinessCheckException {
        return articleService.addArticle(articleDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtArticle updateArticle(ArticleDto articleDto) throws BusinessCheckException {
        return articleService.updateArticle(articleDto);
    }
}
