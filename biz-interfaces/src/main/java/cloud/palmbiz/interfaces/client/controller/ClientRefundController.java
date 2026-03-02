package cloud.palmbiz.interfaces.client.controller;

import cloud.palmbiz.application.aftersale.dto.AftersaleDto;
import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.user.dto.UserOrderDto;
import cloud.palmbiz.common.enums.RefundStatusEnum;
import cloud.palmbiz.application.order.service.OrderService;
import cloud.palmbiz.application.payment.refund.service.RefundService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.module.client.request.RefundListRequest;
import cloud.palmbiz.module.client.request.RefundSubmitRequest;
import cloud.palmbiz.infrastructure.model.MtRefund;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 售后类controller
 */
@Api(tags="会员端-售后相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/refund")
public class ClientRefundController extends BaseController {

    /**
     * 售后服务接口
     */
    private RefundService refundService;

    /**
     * 订单服务接口
     */
    private OrderService orderService;

    /**
     * 获取售后订单列表
     */
    @ApiOperation(value = "获取售后订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject list(@ModelAttribute RefundListRequest param) throws BusinessCheckException {
        UserInfoDto userInfo = TokenUtil.getUserInfo();
        param.setUserId(userInfo.getId());
        String status = param.getStatus() != null ? param.getStatus() : "";
        if (status.equals("1")) {
            status = RefundStatusEnum.CREATED.getKey();
        } else {
            status = "";
        }
        Map<String, Object> params = new HashMap();
        params.put("userId", userInfo.getId());
        if (StringUtil.isNotEmpty(status)) {
            params.put("status", status);
        }
        params.put("pageNumber", param.getPage());

        ResponseObject orderData = refundService.getUserRefundList(params);
        return getSuccessResult(orderData.getData());
    }

    /**
     * 售后订单提交
     */
    @ApiOperation(value = "售后订单提交")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject submit(@RequestBody RefundSubmitRequest param) {
        UserInfoDto mtUser = TokenUtil.getUserInfo();
        if (null == mtUser) {
            return getFailureResult(1001);
        }
        param.setUserId(mtUser.getId());

        Integer orderId = param.getOrderId() == null ? 0 : param.getOrderId();
        String remark = param.getRemark() == null ? "" : param.getRemark();
        String type = param.getType() == null ? "" : param.getType();
        List<String> images = param.getImages() == null ? new ArrayList<>() : param.getImages();

        UserOrderDto order = orderService.getOrderById(orderId);
        if (order == null || (!order.getUserId().equals(mtUser.getId()))) {
            return getFailureResult(2001);
        }

        AftersaleDto AftersaleDto = new AftersaleDto();
        AftersaleDto.setUserId(mtUser.getId());
        AftersaleDto.setOrderId(order.getId());
        AftersaleDto.setRemark(remark);
        AftersaleDto.setType(type);
        if (order.getStoreInfo() != null) {
            AftersaleDto.setStoreId(order.getStoreInfo().getId());
        }
        AftersaleDto.setMerchantId(order.getMerchantId());
        if (order.getStoreInfo() != null) {
            AftersaleDto.setStoreId(order.getStoreInfo().getId());
        }
        AftersaleDto.setAmount(order.getPayAmount());
        if (images.size() > 0) {
            AftersaleDto.setImages(String.join(",", images));
        }
        MtRefund refundInfo = refundService.createRefund(AftersaleDto);

        Map<String, Object> outParams = new HashMap();
        outParams.put("refundInfo", refundInfo);

        ResponseObject responseObject = getSuccessResult(outParams);
        return getSuccessResult(responseObject.getData());
    }

    /**
     * 获取售后订单详情
     */
    @ApiOperation(value = "获取售后订单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject detail(HttpServletRequest request) throws BusinessCheckException {
        String refundId = request.getParameter("refundId");
        if (StringUtil.isEmpty(refundId)) {
            return getFailureResult(2000, "售后订单ID不能为空");
        }
        AftersaleDto refundInfo = refundService.getRefundById(Integer.parseInt(refundId));
        return getSuccessResult(refundInfo);
    }

    /**
     * 售后用户发货
     */
    @ApiOperation(value = "售后用户发货")
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject delivery(@RequestBody Map<String, Object> param) throws BusinessCheckException {
        UserInfoDto mtUser = TokenUtil.getUserInfo();
        param.put("userId", mtUser.getId());
        String refundId = param.get("refundId") == null ? "" : param.get("refundId").toString();
        String expressName = param.get("expressName") == null ? "" : param.get("expressName").toString();
        String expressNo = param.get("expressNo") == null ? "" : param.get("expressNo").toString();

        AftersaleDto refundInfo = refundService.getRefundById(Integer.parseInt(refundId));
        if (refundInfo == null || (!refundInfo.getUserId().equals(mtUser.getId()))) {
            return getFailureResult(2001);
        }

        if (StringUtil.isEmpty(expressName) || StringUtil.isEmpty(expressNo)) {
            return getFailureResult(201, "物流信息不能为空");
        }

        AftersaleDto AftersaleDto = new AftersaleDto();
        AftersaleDto.setId(Integer.parseInt(refundId));
        AftersaleDto.setExpressName(expressName);
        AftersaleDto.setExpressNo(expressNo);
        refundService.updateRefund(AftersaleDto);

        return getSuccessResult(true);
    }
}
