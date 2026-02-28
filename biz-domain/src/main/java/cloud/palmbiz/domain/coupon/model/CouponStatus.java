package cloud.palmbiz.domain.coupon.model;

import lombok.Getter;

/**
 * 卡券状态枚举
 *
 * @author DDD Refactoring
 */
@Getter
public enum CouponStatus {

    /**
     * 正常
     */
    ACTIVE("A", "正常"),

    /**
     * 已删除
     */
    DELETED("D", "已删除"),

    /**
     * 已禁用
     */
    DISABLED("N", "已禁用");

    private final String code;
    private final String description;

    CouponStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CouponStatus fromCode(String code) {
        if (code == null) {
            return ACTIVE;
        }
        for (CouponStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return ACTIVE;
    }

    /**
     * 是否可用
     */
    public boolean isAvailable() {
        return this == ACTIVE;
    }

    /**
     * 是否已删除
     */
    public boolean isDeleted() {
        return this == DELETED;
    }
}
