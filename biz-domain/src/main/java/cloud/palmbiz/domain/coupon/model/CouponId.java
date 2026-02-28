package cloud.palmbiz.domain.coupon.model;

import lombok.Value;

/**
 * 卡券ID值对象
 *
 * @author DDD Refactoring
 */
@Value
public class CouponId {

    Integer value;

    private CouponId(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("卡券ID必须是正整数");
        }
        this.value = value;
    }

    public static CouponId of(Integer value) {
        return new CouponId(value);
    }

    public Integer toInteger() {
        return value;
    }
}
