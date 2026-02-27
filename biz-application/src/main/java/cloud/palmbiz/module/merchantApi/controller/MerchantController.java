package cloud.palmbiz.module.merchantApi.controller;

import cloud.palmbiz.common.dto.StaffDto;
import cloud.palmbiz.common.dto.UserInfo;
import cloud.palmbiz.common.service.ConfirmLogService;
import cloud.palmbiz.common.service.MemberService;
import cloud.palmbiz.common.service.OrderService;
import cloud.palmbiz.common.service.StaffService;
import cloud.palmbiz.common.util.DateUtil;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.repository.model.MtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商户相关controller
 */
@Api(tags="商户端-商户信息相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/merchantApi/merchant")
public class MerchantController extends BaseController {

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 店铺员工服务接口
     */
    private StaffService staffService;

    /**
     * 卡券核销记录服务接口
     */
    private ConfirmLogService confirmLogService;

    /**
     * 订单服务接口
     */
    private OrderService orderService;

    /**
     * 查询商户信息
     */
    @ApiOperation(value = "查询商户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject info() throws BusinessCheckException {
        UserInfo userInfo = TokenUtil.getUserInfo();

        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("userInfo", mtUser);

        StaffDto staffInfo = staffService.getStaffInfoByMobile(mtUser.getMobile());
        if (null == staffInfo) {
            return getFailureResult(1002, "您的帐号不是商户，没有操作权限");
        }

        outParams.put("confirmInfo", staffInfo);

        // 总收款额
        Date beginTime = DateUtil.getDayBegin();
        Date endTime = DateUtil.getDayEnd();
        BigDecimal payMoney = orderService.getPayMoney(staffInfo.getMerchantId(), staffInfo.getStoreId(), beginTime, endTime);
        outParams.put("payMoney", payMoney);

        // 总会员数
        Long userCount = memberService.getUserCount(staffInfo.getMerchantId(), staffInfo.getStoreId());
        outParams.put("userCount", userCount);

        // 今日订单数
        BigDecimal orderCount = orderService.getOrderCount(staffInfo.getMerchantId(), staffInfo.getStoreId(), beginTime, endTime);
        outParams.put("orderCount", orderCount);

        // 核销券数
        Long confirmCount = confirmLogService.getConfirmCount(staffInfo.getMerchantId(), staffInfo.getStoreId(), beginTime, endTime);
        outParams.put("couponCount", confirmCount);

        // 今日活跃会员数
        Long todayUser = memberService.getActiveUserCount(staffInfo.getMerchantId(), staffInfo.getStoreId(), beginTime, endTime);
        outParams.put("todayUser", todayUser);

        return getSuccessResult(outParams);
    }
}
