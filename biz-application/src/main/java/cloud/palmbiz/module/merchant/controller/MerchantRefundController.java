package cloud.palmbiz.module.merchant.controller;

import cloud.palmbiz.common.aftersale.dto.AftersaleDto;
import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.param.RefundDetailParam;
import cloud.palmbiz.common.param.RefundListParam;
import cloud.palmbiz.common.service.MemberService;
import cloud.palmbiz.common.service.RefundService;
import cloud.palmbiz.common.service.StaffService;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtRefund;
import cloud.palmbiz.infrastructure.model.MtStaff;
import cloud.palmbiz.infrastructure.model.MtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 售后类controller
 */
@Api(tags="商户端-售后管理相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/merchant/refund")
public class MerchantRefundController extends BaseController {

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 店铺员工服务接口
     */
    private StaffService staffService;

    /**
     * 售后服务接口
     */
    private RefundService refundService;

    /**
     * 获取售后订单列表
     */
    @ApiOperation(value = "获取售订单后列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject list(@RequestBody RefundListParam params) throws BusinessCheckException, IllegalAccessException {
        UserInfoDto userInfo = TokenUtil.getUserInfo();
        MtStaff staffInfo = staffService.queryStaffByMobile(userInfo.getMobile());

        if (staffInfo == null) {
            return getFailureResult(1001);
        } else {
            params.setMerchantId(staffInfo.getMerchantId());
            params.setStoreId(staffInfo.getStoreId());
        }
        PaginationResponse paginationResponse = refundService.getRefundListByPagination(new PaginationRequest(params.getPage(), params.getPageSize(), CommonUtil.convert(params)));

        Map<String, Object> result = new HashMap<>();
        result.put("paginationResponse", paginationResponse);
        return getSuccessResult(result);
    }

    /**
     * 获取售后订单详情
     */
    @ApiOperation(value = "获取售后订单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject detail(@RequestBody RefundDetailParam param) throws BusinessCheckException {
        UserInfoDto userInfo = TokenUtil.getUserInfo();

        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        MtStaff mtStaff = staffService.queryStaffByMobile(mtUser.getMobile());
        if (mtStaff == null) {
            return getFailureResult(1004);
        }

        Integer refundId = param.getRefundId();
        if (refundId == null || refundId <= 0) {
            return getFailureResult(201, "售后订单不能为空");
        }

        AftersaleDto refundInfo = refundService.getRefundById(refundId);
        return getSuccessResult(refundInfo);
    }

    /**
     * 更新售后订单
     */
    @ApiOperation(value = "更新售后订单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject update(@RequestBody RefundDetailParam param) throws BusinessCheckException {
        UserInfoDto mtUser = TokenUtil.getUserInfo();

        Integer refundId = param.getRefundId();
        if (refundId == null || refundId <= 0) {
            return getFailureResult(201, "售后订单不能为空");
        }

        AftersaleDto refundInfo = refundService.getRefundById(refundId);
        if (refundInfo == null) {
            return getFailureResult(201, "售后订单不存在");
        }

        MtUser userInfo = memberService.queryMemberById(mtUser.getId());
        MtStaff staffInfo = staffService.queryStaffByMobile(userInfo.getMobile());

        if (staffInfo == null || (staffInfo.getStoreId() != null && staffInfo.getStoreId() > 0 && !staffInfo.getStoreId().equals(refundInfo.getStoreInfo().getId()))) {
            return getFailureResult(1004);
        }
        AftersaleDto AftersaleDto = new AftersaleDto();
        AftersaleDto.setId(refundId);
        AftersaleDto.setOperator(staffInfo.getRealName());
        MtRefund mtRefund = refundService.updateRefund(AftersaleDto);
        return getSuccessResult(mtRefund);
    }
}
