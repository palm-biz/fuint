package cloud.palmbiz.interfaces.backendApi.controller;

import cloud.palmbiz.application.identity.account.dto.AccountInfoDto;
import cloud.palmbiz.common.article.dto.ArticleDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.interfaces.param.ArticlePage;
import cloud.palmbiz.interfaces.param.StatusParam;
import cloud.palmbiz.application.article.service.ArticleService;
import cloud.palmbiz.application.SettingService;
import cloud.palmbiz.application.store.service.StoreService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtArticle;
import cloud.palmbiz.infrastructure.model.MtStore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章管理类controller
 */
@Api(tags="管理端-文章相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/article")
public class BackendArticleController extends BaseController {

    /**
     * 文章服务接口
     */
    private ArticleService articleService;

    /**
     * 系统设置服务接口
     */
    private SettingService settingService;

    /**
     * 店铺服务接口
     */
    private StoreService storeService;

    /**
     * 文章列表查询
     */
    @ApiOperation(value = "文章列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:article:index')")
    public ResponseObject list(@ModelAttribute ArticlePage articlePage) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            articlePage.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            articlePage.setStoreId(accountInfo.getStoreId());
        }
        PaginationResponse<ArticleDto> paginationResponse = articleService.queryArticleListByPagination(articlePage);

        // 店铺列表
        List<MtStore> storeList = storeService.getMyStoreList(accountInfo.getMerchantId(), accountInfo.getStoreId(), StatusEnum.ENABLED.getKey());

        Map<String, Object> result = new HashMap<>();
        result.put("dataList", paginationResponse);
        result.put("imagePath", settingService.getUploadBasePath());
        result.put("storeList", storeList);

        return getSuccessResult(result);
    }

    /**
     * 更新文章状态
     */
    @ApiOperation(value = "更新文章状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:article:edit')")
    public ResponseObject updateStatus(@RequestBody StatusParam params) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        String status = params.getStatus() != null ? params.getStatus() : StatusEnum.ENABLED.getKey();

        MtArticle mtArticle = articleService.queryArticleById(params.getId());
        if (mtArticle == null) {
            return getFailureResult(201);
        }

        ArticleDto article = new ArticleDto();
        article.setOperator(accountInfo.getAccountName());
        article.setId(params.getId());
        article.setStatus(status);
        articleService.updateArticle(article);

        return getSuccessResult(true);
    }

    /**
     * 保存文章
     */
    @ApiOperation(value = "保存文章")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:article:add')")
    public ResponseObject saveHandler(@RequestBody ArticleDto articleDto) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        articleDto.setOperator(accountInfo.getAccountName());
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            articleDto.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            articleDto.setStoreId(accountInfo.getStoreId());
        }

        if (articleDto.getId() != null && articleDto.getId() > 0) {
            articleService.updateArticle(articleDto);
        } else {
            articleService.addArticle(articleDto);
        }

        return getSuccessResult(true);
    }

    /**
     * 获取文章详情
     */
    @ApiOperation(value = "获取文章详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:article:index')")
    public ResponseObject info(@PathVariable("id") Integer id) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();

        MtArticle articleInfo = articleService.queryArticleById(id);
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            if (!articleInfo.getMerchantId().equals(accountInfo.getMerchantId())) {
                return getFailureResult(1004);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("articleInfo", articleInfo);
        result.put("imagePath", settingService.getUploadBasePath());

        return getSuccessResult(result);
    }
}
