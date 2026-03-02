package cloud.palmbiz.interfaces.backendApi.controller;

import cloud.palmbiz.application.identity.account.dto.AccountInfoDto;
import cloud.palmbiz.common.page.dto.ParamDto;
import cloud.palmbiz.common.enums.CouponTypeEnum;
import cloud.palmbiz.interfaces.param.ConfirmLogPage;
import cloud.palmbiz.application.confirmlog.service.ConfirmLogService;
import cloud.palmbiz.application.marketing.coupon.service.CouponService;
import cloud.palmbiz.application.member.service.MemberService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员卡券核销流水
 */
@Api(tags="管理端-券核销流水相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/confirmLog")
public class BackendConfirmLogController extends BaseController {

    /**
     * 卡券核销流水接口
     */
    private ConfirmLogService confirmLogService;

    /**
     * 卡券服务接口
     */
    private CouponService couponService;

    /**
     * 会员接口服务
     */
    private MemberService memberService;

    /**
     * 获取会员卡券核销记录列表
     */
    @ApiOperation(value = "获取会员卡券核销记录列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('coupon:confirmLog:index')")
    public ResponseObject list(@ModelAttribute ConfirmLogPage confirmLogPage) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            confirmLogPage.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() > 0 && accountInfo.getStoreId() > 0) {
            confirmLogPage.setStoreId(accountInfo.getStoreId());
        }
        if (StringUtil.isNotEmpty(confirmLogPage.getMobile())) {
            MtUser userInfo = memberService.queryMemberByMobile(accountInfo.getMerchantId(), confirmLogPage.getMobile());
            if (userInfo != null) {
                confirmLogPage.setUserId(userInfo.getId());
            } else {
                confirmLogPage.setUserId(0);
            }
        }

        PaginationResponse<ConfirmLogDto> paginationResponse = confirmLogService.queryConfirmLogListByPagination(confirmLogPage);

        // 卡券类型列表
        List<ParamDto> typeList = CouponTypeEnum.getCouponTypeList();

        Map<String, Object> result = new HashMap<>();
        result.put("paginationResponse", paginationResponse);
        result.put("typeList", typeList);

        return getSuccessResult(result);
    }

    /**
     * 撤销已使用的卡券
     */
    @ApiOperation(value = "撤销已使用的卡券")
    @RequestMapping(value = "/rollbackUserCoupon/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('coupon:confirmLog:index')")
    public ResponseObject rollbackUserCoupon(HttpServletRequest request, @PathVariable("id") Integer id) throws BusinessCheckException {
        String userCouponId = (request.getParameter("userCouponId") == null || StringUtil.isEmpty(request.getParameter("userCouponId"))) ? "0" : request.getParameter("userCouponId");
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        couponService.rollbackUserCoupon(id, Integer.parseInt(userCouponId), accountInfo.getAccountName());
        return getSuccessResult(true);
    }
}
