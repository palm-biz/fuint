package cloud.palmbiz.module.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cloud.palmbiz.common.dto.NavigationDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.service.BannerService;
import cloud.palmbiz.common.service.MerchantService;
import cloud.palmbiz.common.service.SettingService;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtBanner;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面接口controller
 */
@Api(tags="会员端-页面相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/page")
public class ClientPageController extends BaseController {

    /**
     * 焦点图服务接口
     */
    private BannerService bannerService;

    /**
     * 商户服务接口
     */
    private MerchantService merchantService;

    /**
     * 系统设置服务接口
     */
    private SettingService settingService;

    /**
     * 获取页面数据
     */
    @ApiOperation(value = "获取首页页面数据")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject getPageData(HttpServletRequest request, @RequestParam Map<String, Object> param) throws JsonProcessingException {
        String merchantNo = request.getHeader("merchantNo") == null ? "" : request.getHeader("merchantNo");
        Integer storeId = StringUtil.isEmpty(request.getHeader("storeId")) ? 0 : Integer.parseInt(request.getHeader("storeId"));

        Map<String, Object> params = new HashMap<>();
        params.put("status", StatusEnum.ENABLED.getKey());
        if (storeId > 0) {
            params.put("storeId", storeId);
        }
        Integer merchantId = merchantService.getMerchantId(merchantNo);
        if (merchantId > 0) {
            params.put("merchantId", merchantId);
        }

        List<MtBanner> bannerList = bannerService.queryBannerListByParams(params);
        List<NavigationDto> navigation = settingService.getNavigation(merchantId, storeId, StatusEnum.ENABLED.getKey());

        Map<String, Object> outParams = new HashMap();
        outParams.put("banner", bannerList);
        outParams.put("navigation", navigation);
        return getSuccessResult(outParams);
    }
}
