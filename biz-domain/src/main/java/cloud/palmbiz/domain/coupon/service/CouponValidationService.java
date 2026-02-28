package cloud.palmbiz.domain.coupon.service;

import cloud.palmbiz.domain.coupon.model.Coupon;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 卡券验证领域服务
 * 负责卡券相关的业务规则验证
 *
 * @author DDD Refactoring
 */
@Service
public class CouponValidationService {

    /**
     * 验证卡券是否可以创建
     */
    public void validateCouponCreation(String name, Integer total) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("卡券名称不能为空");
        }
        if (total != null && total < 0) {
            throw new IllegalArgumentException("发行数量不能为负数");
        }
    }

    /**
     * 验证卡券是否可以发放
     */
    public void validateCouponSend(Coupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("卡券不存在");
        }
        if (coupon.getStatus().isDeleted()) {
            throw new IllegalStateException("卡券已删除，不能发放");
        }
        if (!coupon.isAvailable()) {
            throw new IllegalStateException("卡券不可用");
        }
        if (coupon.isExpired()) {
            throw new IllegalStateException("卡券已过期");
        }
    }

    /**
     * 验证卡券是否可以使用
     */
    public void validateCouponUsage(Coupon coupon, Date useTime) {
        if (coupon == null) {
            throw new IllegalArgumentException("卡券不存在");
        }
        if (!coupon.isAvailable()) {
            throw new IllegalStateException("卡券不可用");
        }
        if (coupon.isExpired()) {
            throw new IllegalStateException("卡券已过期");
        }
    }

    /**
     * 验证有效期设置
     */
    public void validateValidPeriod(Date beginTime, Date endTime) {
        if (beginTime == null || endTime == null) {
            throw new IllegalArgumentException("有效期开始时间和结束时间不能为空");
        }
        if (beginTime.after(endTime)) {
            throw new IllegalArgumentException("有效期开始时间不能晚于结束时间");
        }
        Date now = new Date();
        if (endTime.before(now)) {
            throw new IllegalArgumentException("结束时间不能早于当前时间");
        }
    }

    /**
     * 验证有效天数设置
     */
    public void validateValidDays(Integer days) {
        if (days == null || days <= 0) {
            throw new IllegalArgumentException("有效天数必须大于0");
        }
        if (days > 3650) {
            throw new IllegalArgumentException("有效天数不能超过10年");
        }
    }

    /**
     * 验证限领数量
     */
    public void validateLimitNum(Integer limitNum) {
        if (limitNum != null && limitNum < 0) {
            throw new IllegalArgumentException("限领数量不能为负数");
        }
    }

    /**
     * 验证发行总量
     */
    public void validateTotal(Integer total) {
        if (total != null && total < 0) {
            throw new IllegalArgumentException("发行总量不能为负数");
        }
    }

    /**
     * 验证卡券所属
     */
    public void validateCouponOwnership(Coupon coupon, Integer merchantId) {
        if (coupon == null) {
            throw new IllegalArgumentException("卡券不存在");
        }
        if (merchantId == null) {
            throw new IllegalArgumentException("商户ID不能为空");
        }
        if (!merchantId.equals(coupon.getMerchantId())) {
            throw new IllegalStateException("卡券不属于当前商户");
        }
    }

    /**
     * 验证卡券状态变更
     */
    public void validateStatusChange(Coupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("卡券不存在");
        }
        if (coupon.getStatus().isDeleted()) {
            throw new IllegalStateException("已删除的卡券不能变更状态");
        }
    }
}
