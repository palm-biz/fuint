package cloud.palmbiz.interfaces.merchant.controller;

import cloud.palmbiz.common.order.dto.OrderDto;
import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.user.dto.UserOrderDto;
import cloud.palmbiz.interfaces.param.OrderConfirmParam;
import cloud.palmbiz.interfaces.param.OrderDetailParam;
import cloud.palmbiz.interfaces.param.OrderListParam;
import cloud.palmbiz.application.member.service.MemberService;
import cloud.palmbiz.application.order.service.OrderService;
import cloud.palmbiz.application.staff.service.StaffService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtOrder;
import cloud.palmbiz.infrastructure.model.MtStaff;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 订单类controller
 */
@Api(tags="商户端-订单管理相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/merchant/order")
public class MerchantOrderController extends BaseController {

    /**
     * 订单服务接口
     */
    private OrderService orderService;

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 店铺员工服务接口
     */
    private StaffService staffService;

    /**
     * 获取订单列表
     */
    @ApiOperation(value = "获取订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject list(@RequestBody OrderListParam params) throws BusinessCheckException {
        UserInfoDto userInfo = TokenUtil.getUserInfo();

        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        MtStaff staffInfo = staffService.queryStaffByMobile(mtUser.getMobile());

        if (staffInfo == null) {
            return getFailureResult(1004);
        } else {
            params.setMerchantId(staffInfo.getMerchantId());
            params.setStoreId(staffInfo.getStoreId());
        }

        PaginationResponse orderData = orderService.getUserOrderList(params);
        return getSuccessResult(orderData);
    }

    /**
     * 获取订单详情
     */
    @ApiOperation(value = "获取订单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject detail(@RequestBody OrderDetailParam param) throws BusinessCheckException {
        UserInfoDto userInfo = TokenUtil.getUserInfo();

        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        MtStaff mtStaff = staffService.queryStaffByMobile(mtUser.getMobile());
        if (mtStaff == null) {
            return getFailureResult(1004);
        }

        String orderId = param.getOrderId();
        if (orderId == null || StringUtil.isEmpty(orderId)) {
            return getFailureResult(2000, "订单不能为空");
        }

        UserOrderDto orderInfo = orderService.getMyOrderById(Integer.parseInt(param.getOrderId()));

        return getSuccessResult(orderInfo);
    }

    /**
     * 取消订单
     */
    @ApiOperation(value = "取消订单")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject cancel(@RequestBody OrderDetailParam param) throws BusinessCheckException {
        UserInfoDto mtUser = TokenUtil.getUserInfo();

        String orderId = param.getOrderId();
        if (orderId == null || StringUtil.isEmpty(orderId)) {
            return getFailureResult(201, "订单不能为空");
        }

        UserOrderDto orderDto = orderService.getOrderById(Integer.parseInt(orderId));
        if (orderDto == null) {
            return getFailureResult(201, "订单已不存在");
        }

        MtUser userInfo = memberService.queryMemberById(mtUser.getId());
        MtStaff staffInfo = staffService.queryStaffByMobile(userInfo.getMobile());

        if (staffInfo == null || (staffInfo.getStoreId() != null && staffInfo.getStoreId() > 0 && !staffInfo.getStoreId().equals(orderDto.getStoreInfo().getId()))) {
            return getFailureResult(1004);
        }

        MtOrder orderInfo = orderService.cancelOrder(orderDto.getId(), "店员取消");
        return getSuccessResult(orderInfo);
    }

    /**
     * 核销订单
     */
    @ApiOperation(value = "核销订单")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject confirm(@RequestBody OrderConfirmParam param) throws BusinessCheckException {
        UserInfoDto mtUser = TokenUtil.getUserInfo();

        Integer orderId = param.getOrderId();

        UserOrderDto orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null) {
            return getFailureResult(201, "订单已不存在");
        }
        if (StringUtil.isEmpty(param.getCode())) {
            return getFailureResult(201, "核销码不能为空");
        }

        MtUser userInfo = memberService.queryMemberById(mtUser.getId());
        MtStaff staffInfo = staffService.queryStaffByMobile(userInfo.getMobile());
        if (staffInfo == null || (staffInfo.getStoreId() != null && staffInfo.getStoreId() > 0 && !staffInfo.getStoreId().equals(orderInfo.getStoreInfo().getId()))) {
            return getFailureResult(1004);
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderInfo.getId());
        orderDto.setConfirmRemark(param.getRemark());
        orderDto.setVerifyCode(param.getCode());
        orderDto.setOperator(staffInfo.getRealName());

        orderService.updateOrder(orderDto);
        return getSuccessResult(true);
    }
}
