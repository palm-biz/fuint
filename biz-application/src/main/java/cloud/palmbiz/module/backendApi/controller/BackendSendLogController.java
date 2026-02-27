package cloud.palmbiz.module.backendApi.controller;

import cloud.palmbiz.common.Constants;
import cloud.palmbiz.common.dto.AccountInfo;
import cloud.palmbiz.common.service.CouponService;
import cloud.palmbiz.common.service.MemberService;
import cloud.palmbiz.common.service.SendLogService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtSendLog;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡券发放记录管理类controller
 */
@Api(tags="管理端-卡券发放相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/sendLog")
public class BackendSendLogController extends BaseController {

    /**
     * 发送记录服务接口
     */
    private SendLogService sendLogService;

    /**
     * 会员接口服务
     */
    private MemberService memberService;

    /**
     * 卡券服务接口
     */
    private CouponService couponService;

    /**
     * 查询发券记录列表
     */
    @ApiOperation(value = "查询发券记录列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject list(HttpServletRequest request) throws BusinessCheckException {
        String status = request.getParameter("status") == null ? "" : request.getParameter("status");
        String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
        String mobile = request.getParameter("mobile") == null ? "" : request.getParameter("mobile");
        String couponId = request.getParameter("couponId") == null ? "" : request.getParameter("couponId");
        Integer page = request.getParameter("page") == null ? Constants.PAGE_NUMBER : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("pageSize") == null ? Constants.PAGE_SIZE : Integer.parseInt(request.getParameter("pageSize"));

        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        Map<String, Object> searchParams = new HashMap<>();
        if (StringUtil.isNotEmpty(status)) {
            searchParams.put("status", status);
        }

        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            searchParams.put("merchantId", accountInfo.getMerchantId());
        }

        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            searchParams.put("storeId", accountInfo.getStoreId());
        }

        if (StringUtil.isNotEmpty(userId)) {
            searchParams.put("userId", userId);
        }

        if (StringUtil.isNotEmpty(couponId)) {
            searchParams.put("couponId", couponId);
        }

        if (StringUtil.isNotEmpty(mobile)) {
            MtUser userInfo = memberService.queryMemberByMobile(accountInfo.getMerchantId(), mobile);
            if (userInfo != null) {
                searchParams.put("userId", userInfo.getId().toString());
            } else {
                searchParams.put("userId", "0");
            }
        }
        PaginationResponse<MtSendLog> paginationResponse = sendLogService.querySendLogListByPagination(new PaginationRequest(page, pageSize, searchParams));

        Map<String, Object> result = new HashMap<>();
        result.put("paginationResponse", paginationResponse);

        return getSuccessResult(result);
    }

    /**
     * 废除用户卡券
     */
    @ApiOperation(value = "废除用户卡券")
    @RequestMapping(value = "/removeUserCoupon/{id}", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject removeUserCoupon(@PathVariable("id") Long id) {
        AccountInfo accountInfo = TokenUtil.getAccountInfo();

        if (id == null) {
            return getFailureResult(201, "系统参数有误");
        }

        MtSendLog sendLog = sendLogService.querySendLogById(id);
        if (sendLog == null) {
            return getFailureResult(201, "系统参数有误");
        }

        couponService.removeUserCoupon(id, sendLog.getUuid(), accountInfo.getAccountName());
        return getSuccessResult(true);
    }
}
