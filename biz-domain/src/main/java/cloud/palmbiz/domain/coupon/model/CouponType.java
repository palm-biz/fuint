package cloud.palmbiz.domain.coupon.model;

import lombok.Getter;

/**
 * 卡券类型枚举
 *
 * @author DDD Refactoring
 */
@Getter
public enum CouponType {

    /**
     * 优惠券
     */
    COUPON("C", "优惠券"),

    /**
     * 储值卡
     */
    PRESTORE("P", "储值卡"),

    /**
     * 计次卡
     */
    TIMER("T", "计次卡");

    private final String code;
    private final String description;

    CouponType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CouponType fromCode(String code) {
        if (code == null) {
            return COUPON;
        }
        for (CouponType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return COUPON;
    }

    /**
     * 是否为优惠券
     */
    public boolean isCoupon() {
        return this == COUPON;
    }

    /**
     * 是否为储值卡
     */
    public boolean isPrestore() {
        return this == PRESTORE;
    }

    /**
     * 是否为计次卡
     */
    public boolean isTimer() {
        return this == TIMER;
    }
}
