package com.fuint.domain.model.merchant;

/**
 * 商户状态枚举
 *
 * @author fuint
 * @since 2.0.0
 */
public enum MerchantStatus {

    /**
     * 激活
     */
    ACTIVE("A", "激活"),

    /**
     * 禁用
     */
    DISABLED("D", "禁用");

    private final String code;
    private final String desc;

    MerchantStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据 code 获取枚举
     */
    public static MerchantStatus fromCode(String code) {
        for (MerchantStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return ACTIVE;
    }
}
