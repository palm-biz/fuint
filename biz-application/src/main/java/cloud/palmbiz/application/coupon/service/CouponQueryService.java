package cloud.palmbiz.application.coupon.service;

import cloud.palmbiz.application.coupon.query.CouponPageQuery;
import cloud.palmbiz.domain.coupon.model.Coupon;
import cloud.palmbiz.domain.coupon.model.CouponId;
import cloud.palmbiz.domain.coupon.repository.CouponRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public Coupon queryById(Integer couponId) throws BusinessCheckException {
        if (couponId == null) {
            throw new BusinessCheckException("卡券ID不能为空");
        }

        Coupon coupon = couponRepository.findById(CouponId.of(couponId));
        if (coupon == null) {
            throw new BusinessCheckException("卡券不存在");
        }

        return coupon;
    }

    public List<Coupon> queryByMerchantId(Integer merchantId) {
        return couponRepository.findByMerchantId(merchantId);
    }

    public List<Coupon> queryByStoreId(Integer storeId) {
        return couponRepository.findByStoreId(storeId);
    }

    public List<Coupon> queryByPage(CouponPageQuery query) {
        if (query == null) {
            query = new CouponPageQuery();
        }

        Map<String, Object> params = new HashMap<>();
        if (query.getMerchantId() != null) {
            params.put("merchantId", query.getMerchantId());
        }
        if (query.getStoreId() != null) {
            params.put("storeId", query.getStoreId());
        }
        if (query.getType() != null) {
            params.put("type", query.getType());
        }
        if (query.getStatus() != null) {
            params.put("status", query.getStatus());
        }

        return couponRepository.findByPage(params, query.getPageNumber(), query.getPageSize());
    }

    public Coupon queryByReceiveCode(String receiveCode) throws BusinessCheckException {
        if (receiveCode == null || receiveCode.isEmpty()) {
            throw new BusinessCheckException("领取码不能为空");
        }

        Coupon coupon = couponRepository.findByReceiveCode(receiveCode);
        if (coupon == null) {
            throw new BusinessCheckException("领取码对应的卡券不存在");
        }

        return coupon;
    }

    public List<Coupon> queryByType(Integer merchantId, String type) {
        return couponRepository.findByType(merchantId, type);
    }

    public List<Coupon> queryAvailableCoupons(Integer merchantId) {
        return couponRepository.findAvailableCoupons(merchantId);
    }

    public Long countByCondition(Map<String, Object> params) {
        return couponRepository.countByCondition(params);
    }
}
