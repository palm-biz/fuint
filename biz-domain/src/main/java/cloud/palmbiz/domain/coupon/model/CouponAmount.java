package cloud.palmbiz.domain.coupon.model;

import lombok.Value;

import java.math.BigDecimal;

/**
 * 卡券面额值对象
 *
 * @author DDD Refactoring
 */
@Value
public class CouponAmount {

    BigDecimal value;

    private CouponAmount(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("卡券面额不能为负数");
        }
        this.value = value;
    }

    public static CouponAmount of(BigDecimal value) {
        return new CouponAmount(value);
    }

    public static CouponAmount zero() {
        return new CouponAmount(BigDecimal.ZERO);
    }

    public BigDecimal toBigDecimal() {
        return value;
    }

    /**
     * 是否有效（大于0）
     */
    public boolean isValid() {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }
}
