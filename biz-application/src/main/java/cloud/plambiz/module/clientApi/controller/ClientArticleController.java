package cloud.plambiz.module.clientApi.controller;

import cloud.plambiz.common.dto.ArticleDto;
import cloud.plambiz.common.enums.StatusEnum;
import cloud.plambiz.common.param.ArticleDetailParam;
import cloud.plambiz.common.param.ArticlePage;
import cloud.plambiz.common.service.ArticleService;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.mapper.MtArticleMapper;
import cloud.plambiz.utils.StringUtil;
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
