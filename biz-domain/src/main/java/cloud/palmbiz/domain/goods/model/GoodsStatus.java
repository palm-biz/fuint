package cloud.palmbiz.domain.goods.model;

import lombok.Getter;

/**
 * 商品状态枚举
 *
 * @author DDD Refactoring
 */
@Getter
public enum GoodsStatus {

    /**
     * 正常
     */
    ACTIVE("A", "正常"),

    /**
     * 已删除
     */
    DELETED("D", "已删除"),

    /**
     * 已下架
     */
    INACTIVE("N", "已下架");

    private final String code;
    private final String description;

    GoodsStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static GoodsStatus fromCode(String code) {
        if (code == null) {
            return ACTIVE;
        }
        for (GoodsStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return ACTIVE;
    }

    /**
     * 是否可售
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
