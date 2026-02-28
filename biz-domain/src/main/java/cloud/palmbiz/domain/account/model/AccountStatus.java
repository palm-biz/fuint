package cloud.palmbiz.domain.account.model;

import lombok.Getter;

/**
 * 账号状态枚举
 *
 * @author DDD Refactoring
 */
@Getter
public enum AccountStatus {

    /**
     * 已删除（软删除）
     */
    DELETED(-1, "已删除"),

    /**
     * 已禁用
     */
    DISABLED(0, "已禁用"),

    /**
     * 已启用
     */
    ENABLED(1, "已启用");

    private final Integer code;
    private final String description;

    AccountStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据状态码获取枚举
     */
    public static AccountStatus fromCode(Integer code) {
        if (code == null) {
            return DISABLED;
        }
        for (AccountStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return DISABLED;
    }

    /**
     * 是否为激活状态
     */
    public boolean isActive() {
        return this == ENABLED;
    }
}
