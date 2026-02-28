package cloud.palmbiz.domain.member.model;

import lombok.Getter;

/**
 * 会员状态枚举
 *
 * @author DDD Refactoring
 */
@Getter
public enum MemberStatus {

    /**
     * 激活
     */
    ENABLED("A", "激活"),

    /**
     * 禁用
     */
    DISABLED("N", "禁用"),

    /**
     * 删除
     */
    DELETED("D", "删除"),

    /**
     * 禁止
     */
    FORBIDDEN("F", "禁止");

    private final String code;
    private final String description;

    MemberStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MemberStatus fromCode(String code) {
        if (code == null) {
            return DISABLED;
        }
        for (MemberStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return DISABLED;
    }

    public boolean isActive() {
        return this == ENABLED;
    }
}
