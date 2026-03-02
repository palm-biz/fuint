package cloud.palmbiz.application.coupongroup.service;

import cloud.palmbiz.common.service.CouponGroupService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtCouponGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CouponGroupQueryService {

    private final CouponGroupService couponGroupService;

    public PaginationResponse<MtCouponGroup> queryCouponGroupListByPagination(PaginationRequest paginationRequest) {
        return couponGroupService.queryCouponGroupListByPagination(paginationRequest);
    }

    public MtCouponGroup queryCouponGroupById(Integer id) {
        return couponGroupService.queryCouponGroupById(id);
    }

    public Integer getCouponNum(Integer id) {
        return couponGroupService.getCouponNum(id);
    }

    public BigDecimal getCouponMoney(Integer id) {
        return couponGroupService.getCouponMoney(id);
    }

    public Integer getSendNum(Integer id) {
        return couponGroupService.getSendNum(id);
    }
}
