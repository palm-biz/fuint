package cloud.plambiz.module.clientApi.controller;

import cloud.plambiz.common.dto.CommissionRelationDto;
import cloud.plambiz.common.dto.UserInfo;
import cloud.plambiz.common.enums.StatusEnum;
import cloud.plambiz.common.param.CommissionRelationPage;
import cloud.plambiz.common.service.CommissionRelationService;
import cloud.plambiz.common.service.MemberService;
import cloud.plambiz.common.service.MerchantService;
import cloud.plambiz.common.service.WeixinService;
import cloud.plambiz.common.util.TokenUtil;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.MtUser;
import cloud.plambiz.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 邀请controller
 */
@Api(tags="会员端-邀请相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/clientApi/share")
public class ClientShareController extends BaseController {

    private Environment env;

    /**
     * 分佣提成关系服务接口
     * */
    private CommissionRelationService commissionRelationService;

    /**
     * 微信相关服务接口
     * */
    private WeixinService weixinService;

    /**
     * 商户服务接口
     */
    private MerchantService merchantService;

    /**
     * 会员服务接口
     * */
    private MemberService memberService;

    /**
     * 获取邀请列表
     */
    @ApiOperation(value="获取邀请列表", notes="获取邀请列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject list(HttpServletRequest request,  @RequestBody CommissionRelationPage commissionRelationPage) throws BusinessCheckException {
        String merchantNo = request.getHeader("merchantNo") == null ? "" : request.getHeader("merchantNo");
        UserInfo userInfo = TokenUtil.getUserInfo();

        commissionRelationPage.setStatus(StatusEnum.ENABLED.getKey());
        commissionRelationPage.setUserId(userInfo.getId());
        if (StringUtil.isNotEmpty(merchantNo)) {
            commissionRelationPage.setMerchantNo(merchantNo);
        }

        PaginationResponse<CommissionRelationDto> paginationResponse = commissionRelationService.queryRelationByPagination(commissionRelationPage);
        Map<String, Object> outParams = new HashMap();
        String url = env.getProperty("website.url");
        outParams.put("url", url);
        outParams.put("paginationResponse", paginationResponse);

        return getSuccessResult(outParams);
    }

    /**
     * 生成小程序链接
     */
    @ApiOperation(value = "生成小程序链接")
    @RequestMapping(value = "/getMiniAppLink", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject getMiniAppLink(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        UserInfo mtUser = TokenUtil.getUserInfo();
        String path = param.get("path") == null ? "" : param.get("path").toString();
        String query = param.get("query") == null ? "" : param.get("query").toString();
        Integer merchantId = merchantService.getMerchantId(request.getHeader("merchantNo"));

        if (merchantId == null || merchantId <= 0) {
            MtUser userInfo = memberService.queryMemberById(mtUser.getId());
            if (userInfo != null) {
                merchantId = userInfo.getMerchantId();
            }
        }

        String link = weixinService.createMiniAppLink(merchantId, path + query);

        Map<String, Object> outParams = new HashMap();
        outParams.put("link", link);

        ResponseObject responseObject = getSuccessResult(outParams);
        return getSuccessResult(responseObject.getData());
    }
}
