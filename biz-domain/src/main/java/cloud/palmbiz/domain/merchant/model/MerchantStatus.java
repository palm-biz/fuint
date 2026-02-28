package cloud.palmbiz.domain.merchant.model;

import lombok.Getter;

/**
 * 商户状态枚举
 */
@Getter
public enum MerchantStatus {
    ENABLED("A", "启用"),
    DISABLED("D", "禁用"),
    DELETED("N", "删除");

    private final String code;
    private final String description;

    MerchantStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MerchantStatus fromCode(String code) {
        for (MerchantStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的商户状态: " + code);
    }

    public boolean isActive() {
        return this == ENABLED;
    }
}
