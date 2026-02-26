package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.dto.ArticleDto;
import cloud.palmbiz.common.param.ArticlePage;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.repository.model.MtArticle;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import java.util.List;
import java.util.Map;

/**
 * 文章业务接口
 */
public interface ArticleService extends IService<MtArticle> {

    /**
     * 分页查询文章列表
     *
     * @param articlePage
     * @return
     */
    PaginationResponse<ArticleDto> queryArticleListByPagination(ArticlePage articlePage);

    /**
     * 添加文章
     *
     * @param  articleDto
     * @throws BusinessCheckException
     */
    MtArticle addArticle(ArticleDto articleDto) throws BusinessCheckException;

    /**
     * 根据ID获取文章信息
     *
     * @param  id 文章ID
     * @return
     */
    MtArticle queryArticleById(Integer id);

    /**
     * 根据ID获取文章详情
     *
     * @param  id 文章ID
     */
    ArticleDto getArticleDetail(Integer id);

    /**
     * 更新文章
     * @param  articleDto
     * @throws BusinessCheckException
     * @return
     * */
    MtArticle updateArticle(ArticleDto articleDto) throws BusinessCheckException;

    /**
     * 根据条件搜索文章
     *
     * @param params
     * @return
     * */
    List<MtArticle> queryArticleListByParams(Map<String, Object> params) throws BusinessCheckException;

}
