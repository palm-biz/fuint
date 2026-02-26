package cloud.plambiz.module.merchantApi.controller;

import cloud.plambiz.common.dto.UserInfo;
import cloud.plambiz.common.dto.UserOrderDto;
import cloud.plambiz.common.param.RechargeParam;
import cloud.plambiz.common.service.*;
import cloud.plambiz.common.util.TokenUtil;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.MtOrder;
import cloud.plambiz.repository.model.MtStaff;
import cloud.plambiz.repository.model.MtUser;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 余额接口controller
 */
@Api(tags="商户端-余额相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/merchantApi/balance")
public class MerchantBalanceController extends BaseController {

    /**
     * 订单服务接口
     * */
    private OrderService orderService;

    /**
     * 支付服务接口
     * */
    private PaymentService paymentService;

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 店铺员工服务接口
     * */
    private StaffService staffService;

    /**
     * 商户服务接口
     * */
    private MerchantService merchantService;

    /**
     * 充值余额
     * */
    @RequestMapping(value = "/doRecharge", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject doRecharge(HttpServletRequest request, @RequestBody RechargeParam rechargeParam) throws BusinessCheckException {
        Integer merchantId = merchantService.getMerchantId(request.getHeader("merchantNo"));
        UserInfo userInfo = TokenUtil.getUserInfo();
        MtStaff staffInfo = null;
        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        if (mtUser != null && mtUser.getMobile() != null) {
            staffInfo = staffService.queryStaffByMobile(mtUser.getMobile());
        }
        if (staffInfo == null) {
            return getFailureResult(201, "您的帐号不是商户，没有操作权限");
        }
        if (!merchantId.equals(staffInfo.getMerchantId())) {
            return getFailureResult(201, "您没有操作权限");
        }
        MtOrder mtOrder = orderService.doRecharge(request, rechargeParam);
        Boolean result = false;
        if (mtOrder != null) {
            UserOrderDto orderInfo = orderService.getOrderByOrderSn(mtOrder.getOrderSn());
            if (orderInfo != null) {
                result = paymentService.paymentCallback(orderInfo);
            }
        }
        return getSuccessResult(result);
    }
}
