package cloud.palmbiz.module.clientApi.controller;

import cloud.palmbiz.common.dto.ArticleDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.param.ArticleDetailParam;
import cloud.palmbiz.common.param.ArticlePage;
import cloud.palmbiz.common.service.ArticleService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.repository.mapper.MtArticleMapper;
import cloud.palmbiz.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章controller
 */
@Api(tags="会员端-文章相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/clientApi/article")
public class ClientArticleController extends BaseController {

    private MtArticleMapper articleMapper;

    /**
     * 文章服务接口
     * */
    private ArticleService articleService;

    /**
     * 获取文章列表
     */
    @ApiOperation(value="获取文章列表", notes="获取文章列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject list(HttpServletRequest request, @RequestBody ArticlePage articlePage) throws BusinessCheckException {
        String merchantNo = request.getHeader("merchantNo") == null ? "" : request.getHeader("merchantNo");

        articlePage.setStatus(StatusEnum.ENABLED.getKey());
        if (StringUtil.isNotEmpty(merchantNo)) {
            articlePage.setMerchantNo(merchantNo);
        }
        PaginationResponse<ArticleDto> paginationResponse = articleService.queryArticleListByPagination(articlePage);

        Map<String, Object> outParams = new HashMap();
        outParams.put("content", paginationResponse.getContent());
        outParams.put("pageSize", paginationResponse.getPageSize());
        outParams.put("pageNumber", paginationResponse.getCurrentPage());
        outParams.put("totalRow", paginationResponse.getTotalElements());
        outParams.put("totalPage", paginationResponse.getTotalPages());

        return getSuccessResult(outParams);
    }

    /**
     * 获取文章详情
     */
    @ApiOperation(value="获取文章详情", notes="根据ID获取文章详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject detail(@RequestBody ArticleDetailParam articleDetailParam) throws BusinessCheckException {
        Integer articleId = articleDetailParam.getArticleId() == null ? 0 : articleDetailParam.getArticleId();
        // 更新阅读点击数
        ArticleDto mtArticle = articleService.getArticleDetail(articleId);
        if (mtArticle != null && mtArticle.getId() != null) {
            articleMapper.increaseClick(mtArticle.getId());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("articleInfo", mtArticle);

        return getSuccessResult(result);
    }
}
