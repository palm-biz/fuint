package cloud.palmbiz.interfaces.client.controller;

import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.interfaces.param.ConfirmParam;
import cloud.palmbiz.application.marketing.coupon.service.CouponService;
import cloud.palmbiz.application.member.service.MemberService;
import cloud.palmbiz.application.staff.service.StaffService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.mapper.MtUserCouponMapper;
import cloud.palmbiz.infrastructure.model.MtCoupon;
import cloud.palmbiz.infrastructure.model.MtStaff;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.infrastructure.model.MtUserCoupon;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卡券核销controller
 */
@Api(tags="会员端-卡券核销相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/confirm")
public class ClientConfirmController extends BaseController {

    private MtUserCouponMapper mtUserCouponMapper;

    /**
     * 卡券服务接口
     */
    private CouponService couponService;

    /**
     * 员工服务接口
     */
    private StaffService staffService;

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 核销卡券
     */
    @ApiOperation(value = "核销卡券")
    @RequestMapping(value = "/doConfirm", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject doConfirm(@RequestBody ConfirmParam confirmParam) {
        String code = confirmParam.getCode() == null ? "" : confirmParam.getCode();
        String amount = (confirmParam.getAmount() == null || confirmParam.getAmount() == "") ? "0" : confirmParam.getAmount();
        String remark = confirmParam.getRemark() == null ? "" : confirmParam.getRemark();

        UserInfoDto loginInfo = TokenUtil.getUserInfo();
        if (loginInfo == null) {
            return getFailureResult(1001);
        }

        MtUserCoupon userCoupon = mtUserCouponMapper.findByCode(code);
        if (null == userCoupon) {
            return getFailureResult(1003, "该券不存在！");
        }

        // 券码已过期
        if (couponService.codeExpired(code)) {
            return getFailureResult(1003, "二维码已过期，请重新获取！");
        }
        MtUser mtUser = memberService.queryMemberById(loginInfo.getId());
        MtCoupon couponInfo = couponService.queryCouponById(userCoupon.getCouponId());

        // 员工是否已经被审核
        HashMap params = new HashMap<>();
        params.put("MOBILE", mtUser.getMobile());
        params.put("AUDITED_STATUS", StatusEnum.ENABLED.getKey());
        List<MtStaff> staffList = staffService.queryStaffByParams(params);
        Integer storeId = 0;
        if (staffList.size() > 0) {
            for (MtStaff staff : staffList) {
                if (staff.getStoreId() > 0) {
                    storeId = staff.getStoreId();
                }
                String storeIdsStr = couponInfo.getStoreIds();
                if (StringUtil.isNotEmpty(storeIdsStr) && storeId > 0) {
                    String[] storeIds = couponInfo.getStoreIds().split(",");
                    Boolean isSameStore = false;
                    for (String hid : storeIds) {
                        if (staff.getStoreId().toString().equals(hid)) {
                            isSameStore = true;
                            break;
                        }
                    }
                    if (!isSameStore) {
                        return getFailureResult(1003, "抱歉，该卡券存在店铺使用范围限制，您所在的店铺无法核销！");
                    }
                }
            }
        } else {
            return getFailureResult(1003, "员工状态异常！");
        }

        Integer userCouponId = userCoupon.getId();
        String confirmCode = "";

        try {
            confirmCode = couponService.useCoupon(userCouponId, mtUser.getId(), storeId, 0, new BigDecimal(amount), remark);
        } catch (BusinessCheckException e) {
            return getFailureResult(1003, e.getMessage());
        }

        // 获取最新余额
        MtUserCoupon userCouponNew = mtUserCouponMapper.selectById(userCoupon.getId());

        // 组织返回参数
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("money", couponInfo.getAmount());
        result.put("balance", userCouponNew.getBalance());
        result.put("tips", "");
        result.put("name", couponInfo.getName());
        result.put("code", confirmCode);
        result.put("status", userCouponNew.getStatus());

        return getSuccessResult(result);
    }
}
