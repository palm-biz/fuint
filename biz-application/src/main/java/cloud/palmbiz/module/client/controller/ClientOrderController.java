package cloud.palmbiz.module.client.controller;

import cloud.palmbiz.common.dto.OrderDto;
import cloud.palmbiz.common.dto.UserInfo;
import cloud.palmbiz.common.dto.UserOrderDto;
import cloud.palmbiz.common.enums.OrderStatusEnum;
import cloud.palmbiz.common.param.OrderListParam;
import cloud.palmbiz.common.service.OrderService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.repository.model.MtOrder;
import cloud.palmbiz.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单类controller
 */
@Api(tags="会员端-订单相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/order")
public class ClientOrderController extends BaseController {

    /**
     * 订单服务接口
     */
    private OrderService orderService;

    /**
     * 获取我的订单列表
     */
    @ApiOperation(value = "获取我的订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject list(@RequestBody OrderListParam orderListParam) throws BusinessCheckException {
        UserInfo userInfo = TokenUtil.getUserInfo();
        orderListParam.setUserId(userInfo.getId().toString());
        PaginationResponse orderData = orderService.getUserOrderList(orderListParam);
        return getSuccessResult(orderData);
    }

    /**
     * 获取订单详情
     */
    @ApiOperation(value = "获取订单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject detail(HttpServletRequest request) throws BusinessCheckException {
        String orderId = request.getParameter("orderId");
        if (StringUtil.isEmpty(orderId)) {
            return getFailureResult(2000, "订单不能为空");
        }
        UserOrderDto orderInfo;
        if (orderId.length() >= 12) {
            orderInfo = orderService.getOrderByOrderSn(orderId);
        } else {
            orderInfo = orderService.getMyOrderById(Integer.parseInt(orderId));
        }
        return getSuccessResult(orderInfo);
    }

    /**
     * 取消订单
     */
    @ApiOperation(value = "取消订单")
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject cancel(HttpServletRequest request) throws BusinessCheckException {
        UserInfo mtUser = TokenUtil.getUserInfo();

        String orderId = request.getParameter("orderId");
        if (StringUtil.isEmpty(orderId)) {
            return getFailureResult(2000, "订单不能为空");
        }

        if (orderId.length() >= 12) {
            MtOrder mtOrder = orderService.getOrderInfoByOrderSn(orderId);
            if (mtOrder != null) {
                orderId = mtOrder.getId().toString();
            }
        }

        UserOrderDto order = orderService.getOrderById(Integer.parseInt(orderId));
        if (!order.getUserId().equals(mtUser.getId())) {
            return getFailureResult(2000, "订单信息有误");
        }

        MtOrder orderInfo = orderService.cancelOrder(order.getId(), "会员取消");
        return getSuccessResult(orderInfo);
    }

    /**
     * 确认收货
     */
    @ApiOperation(value = "确认收货")
    @RequestMapping(value = "/receipt", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject receipt(HttpServletRequest request) throws BusinessCheckException {
        UserInfo mtUser = TokenUtil.getUserInfo();
        if (mtUser == null) {
            return getFailureResult(1001, "用户未登录");
        }

        String orderId = request.getParameter("orderId");
        if (StringUtil.isEmpty(orderId)) {
            return getFailureResult(2000, "订单不能为空");
        }

        UserOrderDto order = orderService.getOrderById(Integer.parseInt(orderId));
        if (!order.getUserId().equals(mtUser.getId())) {
            return getFailureResult(2000, "订单信息有误");
        }

        OrderDto reqDto = new OrderDto();
        reqDto.setId(Integer.parseInt(orderId));
        reqDto.setStatus(OrderStatusEnum.RECEIVED.getKey());
        MtOrder orderInfo = orderService.updateOrder(reqDto);

        return getSuccessResult(orderInfo);
    }

    /**
     * 获取待办订单数量
     */
    @ApiOperation(value = "获取待办订单数量")
    @RequestMapping(value = "/todoCounts", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject todoCounts() {
        UserInfo userInfo = TokenUtil.getUserInfo();

        Map<String, Object> result = new HashMap<>();
        if (userInfo != null) {
            Map<String, Object> param = new HashMap<>();
            param.put("status", OrderStatusEnum.CREATED.getKey());
            param.put("user_id", userInfo.getId());
            List<MtOrder> data = orderService.getOrderListByParams(param);
            result.put("toPay", data.size());
        }

        return getSuccessResult(result);
    }
}
