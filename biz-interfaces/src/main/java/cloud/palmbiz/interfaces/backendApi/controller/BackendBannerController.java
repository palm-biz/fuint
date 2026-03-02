package cloud.palmbiz.interfaces.backendApi.controller;

import cloud.palmbiz.application.identity.account.dto.AccountInfoDto;
import cloud.palmbiz.interfaces.param.BannerPage;
import cloud.palmbiz.interfaces.param.StatusParam;
import cloud.palmbiz.application.store.service.StoreService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.common.banner.dto.BannerDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.application.SettingService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.application.banner.service.BannerService;
import cloud.palmbiz.infrastructure.model.MtBanner;
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
 * 焦点图管理类controller
 */
@Api(tags = "管理端-焦点图相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/banner")
public class BackendBannerController extends BaseController {

    /**
     * 焦点图服务接口
     */
    private BannerService bannerService;

    /**
     * 系统设置服务接口
     */
    private SettingService settingService;

    /**
     * 店铺服务接口
     */
    private StoreService storeService;

    /**
     * 焦点图列表查询
     */
    @ApiOperation(value = "焦点图列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:banner:list')")
    public ResponseObject list(@ModelAttribute BannerPage bannerPage) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            bannerPage.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            bannerPage.setStoreId(accountInfo.getStoreId());
        }
        PaginationResponse<MtBanner> paginationResponse = bannerService.queryBannerListByPagination(bannerPage);
        List<MtStore> storeList = storeService.getMyStoreList(accountInfo.getMerchantId(), accountInfo.getStoreId(), StatusEnum.ENABLED.getKey());

        Map<String, Object> result = new HashMap<>();
        result.put("dataList", paginationResponse);
        result.put("imagePath", settingService.getUploadBasePath());
        result.put("storeList", storeList);

        return getSuccessResult(result);
    }

    /**
     * 更新焦点图状态
     */
    @ApiOperation(value = "更新焦点图状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:banner:edit')")
    public ResponseObject updateStatus(@RequestBody StatusParam params) throws BusinessCheckException {
        String status = params.getStatus() != null ? params.getStatus() : StatusEnum.ENABLED.getKey();

        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        MtBanner mtBanner = bannerService.queryBannerById(params.getId());
        if (mtBanner == null) {
            return getFailureResult(201, "该数据不存在");
        }

        BannerDto bannerDto = new BannerDto();
        bannerDto.setOperator(accountInfo.getAccountName());
        bannerDto.setId(params.getId());
        bannerDto.setStatus(status);
        bannerService.updateBanner(bannerDto);

        return getSuccessResult(true);
    }

    /**
     * 保存焦点图
     */
    @ApiOperation(value = "保存焦点图")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:banner:add')")
    public ResponseObject saveHandler(@RequestBody BannerDto bannerDto) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        bannerDto.setOperator(accountInfo.getAccountName());
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            bannerDto.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            bannerDto.setStoreId(accountInfo.getStoreId());
        }
        if (bannerDto.getId() != null && bannerDto.getId() > 0) {
            bannerService.updateBanner(bannerDto);
        } else {
            bannerService.addBanner(bannerDto);
        }
        return getSuccessResult(true);
    }

    /**
     * 获取焦点图详情
     */
    @ApiOperation(value = "获取焦点图详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('content:banner:list')")
    public ResponseObject info(@PathVariable("id") Integer id) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();

        MtBanner bannerInfo = bannerService.queryBannerById(id);
        String imagePath = settingService.getUploadBasePath();

        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            if (!bannerInfo.getMerchantId().equals(accountInfo.getMerchantId())) {
                return getFailureResult(1004);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("bannerInfo", bannerInfo);
        result.put("imagePath", imagePath);

        return getSuccessResult(result);
    }
}
