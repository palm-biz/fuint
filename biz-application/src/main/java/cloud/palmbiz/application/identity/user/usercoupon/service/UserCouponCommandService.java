package cloud.palmbiz.application.identity.user.usercoupon.service;

import cloud.palmbiz.application.identity.user.service.UserCouponService;
import cloud.palmbiz.common.usercoupon.param.CouponReceiveParam;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserCouponCommandService {

    private final UserCouponService userCouponService;

    @Transactional(rollbackFor = Exception.class)
    public boolean receiveCoupon(CouponReceiveParam couponReceiveParam) throws BusinessCheckException {
        return userCouponService.receiveCoupon(couponReceiveParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean preStore(Map<String, Object> paramMap) throws BusinessCheckException {
        return userCouponService.preStore(paramMap);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean buyCouponItem(Integer orderId, Integer couponId, Integer userId, String mobile, Double num) {
        return userCouponService.buyCouponItem(orderId, couponId, userId, mobile, num);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeUserCouponByCouponId(Integer couponId) {
        userCouponService.removeUserCouponByCouponId(couponId);
    }
}
