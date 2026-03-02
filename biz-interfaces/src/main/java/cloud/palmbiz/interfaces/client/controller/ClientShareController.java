package cloud.palmbiz.interfaces.client.controller;

import cloud.palmbiz.common.commission.dto.CommissionRelationDto;
import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.interfaces.param.CommissionRelationPage;
import cloud.palmbiz.application.marketing.commission.service.CommissionRelationService;
import cloud.palmbiz.application.member.service.MemberService;
import cloud.palmbiz.application.merchant.service.MerchantService;
import cloud.palmbiz.application.payment.service.WeixinService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.common.utils.StringUtil;
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
@RequestMapping(value = "/client/share")
public class ClientShareController extends BaseController {

    private Environment env;

    /**
     * 分佣提成关系服务接口
     */
    private CommissionRelationService commissionRelationService;

    /**
     * 微信相关服务接口
     */
    private WeixinService weixinService;

    /**
     * 商户服务接口
     */
    private MerchantService merchantService;

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 获取邀请列表
     */
    @ApiOperation(value="获取邀请列表", notes="获取邀请列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject list(HttpServletRequest request,  @RequestBody CommissionRelationPage commissionRelationPage) throws BusinessCheckException {
        String merchantNo = request.getHeader("merchantNo") == null ? "" : request.getHeader("merchantNo");
        UserInfoDto userInfo = TokenUtil.getUserInfo();

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
        UserInfoDto mtUser = TokenUtil.getUserInfo();
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
