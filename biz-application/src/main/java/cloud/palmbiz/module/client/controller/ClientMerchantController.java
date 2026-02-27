package cloud.palmbiz.module.client.controller;

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
import cloud.palmbiz.repository.model.MtStaff;
import cloud.palmbiz.repository.model.MtUser;
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
@RequestMapping(value = "/client/merchant")
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
     * @param request Request对象
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

        // 商户 ID
        Integer merchantId=confirmInfo.getMerchantId();
        // 门店 ID
        Integer storeId=confirmInfo.getStoreId();
        // 开始时间
        Date beginTime = DateUtil.getDayBegin();
        // 结束时间
        Date endTime = DateUtil.getDayEnd();
        // 收款额
        BigDecimal payMoney = orderService.getPayMoney(merchantId, storeId, beginTime, endTime);
        outParams.put("payMoney", payMoney);

        // 总会员数
        Long userCount = memberService.getUserCount(merchantId, storeId);
        outParams.put("userCount", userCount);

        // 今日订单数
        BigDecimal orderCount = orderService.getOrderCount(merchantId, storeId, beginTime, endTime);
        outParams.put("orderCount", orderCount);

        // 核销券数
        Long confirmCount = confirmLogService.getConfirmCount(merchantId, storeId, beginTime, endTime);
        outParams.put("couponCount", confirmCount);

        // 今日活跃会员数
        Long todayUser = memberService.getActiveUserCount(merchantId, storeId, beginTime, endTime);
        outParams.put("todayUser", todayUser);

        return getSuccessResult(outParams);
    }
}
