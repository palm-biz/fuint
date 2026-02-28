package cloud.palmbiz.domain.order.model;

import lombok.Value;

import java.math.BigDecimal;

/**
 * 订单金额值对象
 * 封装订单金额相关的计算逻辑
 *
 * @author DDD Refactoring
 */
@Value
public class OrderAmount {

    /**
     * 订单原始金额
     */
    BigDecimal amount;

    /**
     * 实付金额
     */
    BigDecimal payAmount;

    /**
     * 折扣金额
     */
    BigDecimal discount;

    /**
     * 积分抵扣金额
     */
    BigDecimal pointAmount;

    /**
     * 配送费
     */
    BigDecimal deliveryFee;

    private OrderAmount(BigDecimal amount, BigDecimal payAmount, BigDecimal discount,
                       BigDecimal pointAmount, BigDecimal deliveryFee) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("订单金额不能为负数");
        }
        if (payAmount == null || payAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("实付金额不能为负数");
        }
        this.amount = amount;
        this.payAmount = payAmount;
        this.discount = discount != null ? discount : BigDecimal.ZERO;
        this.pointAmount = pointAmount != null ? pointAmount : BigDecimal.ZERO;
        this.deliveryFee = deliveryFee != null ? deliveryFee : BigDecimal.ZERO;
    }

    /**
     * 创建订单金额
     */
    public static OrderAmount of(BigDecimal amount, BigDecimal payAmount, BigDecimal discount,
                                 BigDecimal pointAmount, BigDecimal deliveryFee) {
        return new OrderAmount(amount, payAmount, discount, pointAmount, deliveryFee);
    }

    /**
     * 创建简单订单金额（无折扣无积分）
     */
    public static OrderAmount simple(BigDecimal amount) {
        return new OrderAmount(amount, amount, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    /**
     * 计算总金额
     * 总金额 = 原始金额 + 配送费 - 折扣 - 积分抵扣
     */
    public BigDecimal calculateTotal() {
        return amount.add(deliveryFee).subtract(discount).subtract(pointAmount);
    }

    /**
     * 验证实付金额是否正确
     */
    public boolean validatePayAmount() {
        BigDecimal calculated = calculateTotal();
        return payAmount.compareTo(calculated) == 0;
    }

    /**
     * 是否有折扣
     */
    public boolean hasDiscount() {
        return discount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 是否使用积分
     */
    public boolean hasPointDeduction() {
        return pointAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 是否有配送费
     */
    public boolean hasDeliveryFee() {
        return deliveryFee.compareTo(BigDecimal.ZERO) > 0;
    }
}
