package cloud.palmbiz.domain.order.model;

import lombok.Getter;

/**
 * 订单状态枚举
 * 定义订单的生命周期状态
 *
 * @author DDD Refactoring
 */
@Getter
public enum OrderStatus {

    /**
     * 待支付
     */
    CREATED("A", "待支付"),

    /**
     * 已支付
     */
    PAID("B", "已支付"),

    /**
     * 已取消
     */
    CANCEL("C", "已取消"),

    /**
     * 待发货
     */
    DELIVERY("D", "待发货"),

    /**
     * 已发货
     */
    DELIVERED("E", "已发货"),

    /**
     * 已收货
     */
    RECEIVED("F", "已收货"),

    /**
     * 已删除
     */
    DELETED("G", "已删除"),

    /**
     * 已退款
     */
    REFUND("H", "已退款"),

    /**
     * 已完成
     */
    COMPLETE("I", "已完成");

    private final String code;
    private final String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderStatus fromCode(String code) {
        if (code == null) {
            return CREATED;
        }
        for (OrderStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return CREATED;
    }

    /**
     * 是否可以支付
     */
    public boolean canPay() {
        return this == CREATED;
    }

    /**
     * 是否可以取消
     */
    public boolean canCancel() {
        return this == CREATED || this == PAID || this == DELIVERY;
    }

    /**
     * 是否可以发货
     */
    public boolean canDeliver() {
        return this == PAID || this == DELIVERY;
    }

    /**
     * 是否可以收货
     */
    public boolean canReceive() {
        return this == DELIVERED;
    }

    /**
     * 是否可以退款
     */
    public boolean canRefund() {
        return this == PAID || this == DELIVERY || this == DELIVERED || this == RECEIVED;
    }

    /**
     * 是否可以完成
     */
    public boolean canComplete() {
        return this == RECEIVED;
    }

    /**
     * 是否为终态（不可再变更）
     */
    public boolean isFinal() {
        return this == CANCEL || this == DELETED || this == REFUND || this == COMPLETE;
    }

    /**
     * 是否已支付
     */
    public boolean isPaid() {
        return this == PAID || this == DELIVERY || this == DELIVERED ||
               this == RECEIVED || this == COMPLETE;
    }
}
