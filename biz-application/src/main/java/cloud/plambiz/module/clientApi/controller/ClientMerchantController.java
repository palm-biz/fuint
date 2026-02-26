package cloud.plambiz.module.clientApi.controller;

import cloud.plambiz.common.dto.UserInfo;
import cloud.plambiz.common.service.ConfirmLogService;
import cloud.plambiz.common.service.MemberService;
import cloud.plambiz.common.service.OrderService;
import cloud.plambiz.common.service.StaffService;
import cloud.plambiz.common.util.DateUtil;
import cloud.plambiz.common.util.TokenUtil;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.MtStaff;
import cloud.plambiz.repository.model.MtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商家相关controller
 */
@RestController
@RequestMapping(value = "/clientApi/merchant")
public class ClientMerchantController extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ConfirmLogService confirmLogService;

    @Autowired
    private OrderService orderService;

    /**
     * 查询商户信息
     *
     * @param request  Request对象
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject info(HttpServletRequest request) throws BusinessCheckException {
        String token = request.getHeader("Access-Token");
        UserInfo userInfo = TokenUtil.getUserInfoByToken(token);

        if (null == userInfo) {
            return getFailureResult(1001, "用户未登录");
        }

        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("userInfo", mtUser);

        MtStaff confirmInfo = staffService.queryStaffByUserId(userInfo.getId());
        if (null == confirmInfo) {
            return getFailureResult(1002, "该账号不是商户");
        }

        outParams.put("confirmInfo", confirmInfo);

        // 收款额
        Date beginTime = DateUtil.getDayBegin();
        Date endTime = DateUtil.getDayEnd();
        BigDecimal payMoney = orderService.getPayMoney(confirmInfo.getStoreId(), beginTime, endTime);
        outParams.put("payMoney", payMoney);

        // 总会员数
        Long userCount = memberService.getUserCount(confirmInfo.getStoreId());
        outParams.put("userCount", userCount);

        // 今日订单数
        BigDecimal orderCount = orderService.getOrderCount(confirmInfo.getStoreId(), beginTime, endTime);
        outParams.put("orderCount", orderCount);

        // 核销券数
        Long confirmCount = confirmLogService.getConfirmCount(confirmInfo.getStoreId(), beginTime, endTime);
        outParams.put("couponCount", confirmCount);

        // 今日活跃会员数
        Long todayUser = memberService.getActiveUserCount(confirmInfo.getStoreId(), beginTime, endTime);
        outParams.put("todayUser", todayUser);

        return getSuccessResult(outParams);
    }
}
