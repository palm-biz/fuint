package cloud.palmbiz.domain.cart.model;

import lombok.Getter;

/**
 * 购物车状态枚举
 *
 * @author DDD Refactoring
 */
@Getter
public enum CartStatus {

    /**
     * 正常
     */
    ACTIVE("A", "正常"),

    /**
     * 已删除
     */
    DELETED("D", "已删除");

    private final String code;
    private final String description;

    CartStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CartStatus fromCode(String code) {
        if (code == null) {
            return ACTIVE;
        }
        for (CartStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return ACTIVE;
    }

    /**
     * 是否正常
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * 是否已删除
     */
    public boolean isDeleted() {
        return this == DELETED;
    }
}
