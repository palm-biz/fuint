package cloud.palmbiz.domain.store.model;

import lombok.Getter;

/**
 * 店铺状态枚举
 */
@Getter
public enum StoreStatus {
    ENABLED("A", "启用"),
    DISABLED("D", "禁用"),
    DELETED("N", "删除");

    private final String code;
    private final String description;

    StoreStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static StoreStatus fromCode(String code) {
        for (StoreStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的店铺状态: " + code);
    }

    public boolean isActive() {
        return this == ENABLED;
    }
}
