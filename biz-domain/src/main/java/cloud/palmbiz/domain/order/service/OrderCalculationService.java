package cloud.palmbiz.domain.order.service;

import cloud.palmbiz.domain.order.model.Order;
import cloud.palmbiz.domain.order.model.OrderGoods;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单计算领域服务
 * 负责订单金额相关的复杂计算逻辑
 *
 * @author DDD Refactoring
 */
@Service
public class OrderCalculationService {

    /**
     * 计算订单总金额（商品金额）
     */
    public BigDecimal calculateGoodsTotalAmount(List<OrderGoods> goodsList) {
        if (goodsList == null || goodsList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return goodsList.stream()
                .map(OrderGoods::calculateTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 计算实付金额
     * 实付金额 = 商品总额 + 配送费 - 折扣 - 积分抵扣
     */
    public BigDecimal calculatePayAmount(BigDecimal goodsAmount, BigDecimal deliveryFee,
                                        BigDecimal discount, BigDecimal pointAmount) {
        BigDecimal total = goodsAmount;

        // 加上配送费
        if (deliveryFee != null && deliveryFee.compareTo(BigDecimal.ZERO) > 0) {
            total = total.add(deliveryFee);
        }

        // 减去折扣
        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            total = total.subtract(discount);
        }

        // 减去积分抵扣
        if (pointAmount != null && pointAmount.compareTo(BigDecimal.ZERO) > 0) {
            total = total.subtract(pointAmount);
        }

        // 确保不为负数
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }

        return total;
    }

    /**
     * 计算可用积分抵扣金额
     *
     * @param availablePoints 可用积分
     * @param pointToMoneyRate 积分兑换比例（多少积分换1元）
     * @param maxDeductionRate 最大抵扣比例（订单金额的百分比）
     * @param orderAmount 订单原始金额
     * @return 可抵扣金额
     */
    public BigDecimal calculatePointDeductionAmount(Integer availablePoints,
                                                   Integer pointToMoneyRate,
                                                   BigDecimal maxDeductionRate,
                                                   BigDecimal orderAmount) {
        if (availablePoints == null || availablePoints <= 0) {
            return BigDecimal.ZERO;
        }
        if (pointToMoneyRate == null || pointToMoneyRate <= 0) {
            return BigDecimal.ZERO;
        }

        // 计算积分可兑换的金额
        BigDecimal pointAmount = BigDecimal.valueOf(availablePoints)
                .divide(BigDecimal.valueOf(pointToMoneyRate), 2, BigDecimal.ROUND_DOWN);

        // 计算订单最大可抵扣金额
        BigDecimal maxDeduction = orderAmount;
        if (maxDeductionRate != null && maxDeductionRate.compareTo(BigDecimal.ZERO) > 0
                && maxDeductionRate.compareTo(BigDecimal.ONE) <= 0) {
            maxDeduction = orderAmount.multiply(maxDeductionRate).setScale(2, BigDecimal.ROUND_DOWN);
        }

        // 取较小值
        return pointAmount.min(maxDeduction);
    }

    /**
     * 计算订单应得积分
     *
     * @param payAmount 实付金额
     * @param moneyToPointRate 积分获取比例（1元可得多少积分）
     * @return 应得积分
     */
    public Integer calculateEarnedPoints(BigDecimal payAmount, Integer moneyToPointRate) {
        if (payAmount == null || payAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        if (moneyToPointRate == null || moneyToPointRate <= 0) {
            return 0;
        }

        return payAmount.multiply(BigDecimal.valueOf(moneyToPointRate))
                .setScale(0, BigDecimal.ROUND_DOWN)
                .intValue();
    }

    /**
     * 验证订单金额是否合法
     */
    public boolean validateOrderAmount(Order order) {
        if (order == null) {
            return false;
        }

        // 验证实付金额不能为负数
        if (order.getPayAmount().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // 验证金额计算是否正确
        BigDecimal goodsAmount = order.calculateGoodsTotalAmount();
        BigDecimal calculatedPayAmount = calculatePayAmount(
                goodsAmount,
                order.getOrderAmount().getDeliveryFee(),
                order.getOrderAmount().getDiscount(),
                order.getOrderAmount().getPointAmount()
        );

        return order.getPayAmount().compareTo(calculatedPayAmount) == 0;
    }

    /**
     * 计算折扣金额
     *
     * @param originalAmount 原始金额
     * @param discountRate 折扣率（如0.9表示9折）
     * @return 折扣金额
     */
    public BigDecimal calculateDiscountAmount(BigDecimal originalAmount, BigDecimal discountRate) {
        if (originalAmount == null || originalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (discountRate == null || discountRate.compareTo(BigDecimal.ONE) >= 0
                || discountRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        // 折扣金额 = 原始金额 * (1 - 折扣率)
        return originalAmount.multiply(BigDecimal.ONE.subtract(discountRate))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算配送费
     * 根据订单金额、配送距离等因素计算配送费
     *
     * @param orderAmount 订单金额
     * @param freeShippingThreshold 包邮门槛
     * @param baseDeliveryFee 基础配送费
     * @return 配送费
     */
    public BigDecimal calculateDeliveryFee(BigDecimal orderAmount,
                                          BigDecimal freeShippingThreshold,
                                          BigDecimal baseDeliveryFee) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return baseDeliveryFee != null ? baseDeliveryFee : BigDecimal.ZERO;
        }

        // 达到包邮门槛，配送费为0
        if (freeShippingThreshold != null && freeShippingThreshold.compareTo(BigDecimal.ZERO) > 0
                && orderAmount.compareTo(freeShippingThreshold) >= 0) {
            return BigDecimal.ZERO;
        }

        return baseDeliveryFee != null ? baseDeliveryFee : BigDecimal.ZERO;
    }
}
