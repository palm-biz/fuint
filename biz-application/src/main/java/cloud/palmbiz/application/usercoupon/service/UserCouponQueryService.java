package cloud.palmbiz.application.usercoupon.service;

import cloud.palmbiz.common.coupon.dto.CouponDto;
import cloud.palmbiz.common.service.UserCouponService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtUserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserCouponQueryService {

    private final UserCouponService userCouponService;

    public PaginationResponse<MtUserCoupon> queryUserCouponListByPagination(PaginationRequest paginationRequest) {
        return userCouponService.queryUserCouponListByPagination(paginationRequest);
    }

    public List<MtUserCoupon> getUserCouponList(Integer userId, List<String> status) {
        return userCouponService.getUserCouponList(userId, status);
    }

    public List<CouponDto> getPayAbleCouponList(Integer userId, Integer storeId, String useFor) {
        return userCouponService.getPayAbleCouponList(userId, storeId, useFor);
    }

    public List<MtUserCoupon> getUserCouponDetail(Integer userId, Integer couponId) {
        return userCouponService.getUserCouponDetail(userId, couponId);
    }

    public MtUserCoupon getUserCouponDetail(Integer userCouponId) {
        return userCouponService.getUserCouponDetail(userCouponId);
    }

    public List<MtUserCoupon> getUserCouponListByExpireTime(Integer userId, String status, String startTime, String endTime) {
        return userCouponService.getUserCouponListByExpireTime(userId, status, startTime, endTime);
    }
}
