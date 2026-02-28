package cloud.palmbiz.domain.order.service;

import cloud.palmbiz.domain.order.model.Order;
import cloud.palmbiz.domain.order.model.OrderGoods;
import cloud.palmbiz.domain.order.model.OrderStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单验证领域服务
 * 负责订单相关的业务规则验证
 *
 * @author DDD Refactoring
 */
@Service
public class OrderValidationService {

    /**
     * 验证订单是否可以创建
     */
    public void validateOrderCreation(Integer userId, List<OrderGoods> goodsList,
                                     BigDecimal payAmount) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        if (goodsList == null || goodsList.isEmpty()) {
            throw new IllegalArgumentException("订单商品不能为空");
        }

        if (payAmount == null || payAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("支付金额不能为负数");
        }

        // 验证商品信息
        for (OrderGoods goods : goodsList) {
            if (goods.getGoodsId() == null || goods.getGoodsId() <= 0) {
                throw new IllegalArgumentException("商品ID不能为空");
            }
            if (goods.getNum() == null || goods.getNum() <= 0) {
                throw new IllegalArgumentException("商品数量必须大于0");
            }
            if (goods.getPrice() == null || goods.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("商品价格不能为负数");
            }
        }
    }

    /**
     * 验证订单是否可以支付
     */
    public void validateOrderPayment(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }

        if (!order.getStatus().canPay()) {
            throw new IllegalStateException("当前订单状态不允许支付");
        }

        if (order.getPayAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("支付金额必须大于0");
        }
    }

    /**
     * 验证订单是否可以取消
     */
    public void validateOrderCancellation(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }

        if (!order.getStatus().canCancel()) {
            throw new IllegalStateException("当前订单状态不允许取消");
        }

        // 如果已支付，可能需要额外的退款验证
        if (order.isPaid()) {
            // 可以在这里添加额外的退款验证逻辑
        }
    }

    /**
     * 验证订单是否可以发货
     */
    public void validateOrderDelivery(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }

        if (!order.getStatus().canDeliver()) {
            throw new IllegalStateException("当前订单状态不允许发货");
        }

        if (!order.isPaid()) {
            throw new IllegalStateException("未支付的订单不能发货");
        }
    }

    /**
     * 验证订单是否可以核销
     */
    public void validateOrderConfirmation(Order order, String verifyCode) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }

        if (!order.canConfirm()) {
            throw new IllegalStateException("订单不满足核销条件");
        }

        if (verifyCode != null && !verifyCode.equals(order.getVerifyCode())) {
            throw new IllegalStateException("核销码不正确");
        }
    }

    /**
     * 验证积分使用是否合法
     */
    public void validatePointUsage(Integer usePoint, Integer availablePoint,
                                   BigDecimal pointAmount, BigDecimal orderAmount) {
        if (usePoint == null || usePoint < 0) {
            throw new IllegalArgumentException("使用积分不能为负数");
        }

        if (usePoint > 0 && (availablePoint == null || usePoint > availablePoint)) {
            throw new IllegalArgumentException("积分不足");
        }

        if (pointAmount == null || pointAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("积分抵扣金额不能为负数");
        }

        if (pointAmount.compareTo(orderAmount) > 0) {
            throw new IllegalArgumentException("积分抵扣金额不能超过订单金额");
        }
    }

    /**
     * 验证折扣是否合法
     */
    public void validateDiscount(BigDecimal discount, BigDecimal orderAmount) {
        if (discount == null) {
            return;
        }

        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("折扣金额不能为负数");
        }

        if (discount.compareTo(orderAmount) > 0) {
            throw new IllegalArgumentException("折扣金额不能超过订单金额");
        }
    }

    /**
     * 验证订单状态转换是否合法
     */
    public void validateStatusTransition(OrderStatus fromStatus, OrderStatus toStatus) {
        if (fromStatus == null || toStatus == null) {
            throw new IllegalArgumentException("订单状态不能为空");
        }

        if (fromStatus.isFinal()) {
            throw new IllegalStateException("终态订单不能再变更状态");
        }

        // 这里可以添加更详细的状态转换规则验证
        // 例如：CREATED -> PAID -> DELIVERY -> DELIVERED -> RECEIVED -> COMPLETE
    }

    /**
     * 验证订单是否属于指定用户
     */
    public void validateOrderOwnership(Order order, Integer userId) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (!userId.equals(order.getUserId())) {
            throw new IllegalStateException("订单不属于当前用户");
        }
    }

    /**
     * 验证订单是否可以退款
     */
    public void validateOrderRefund(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }

        if (!order.getStatus().canRefund()) {
            throw new IllegalStateException("当前订单状态不允许退款");
        }

        if (!order.isPaid()) {
            throw new IllegalStateException("未支付的订单不能退款");
        }
    }

    /**
     * 验证订单是否可以评价
     */
    public void validateOrderReview(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("订单不能为空");
        }

        OrderStatus status = order.getStatus();
        if (status != OrderStatus.RECEIVED && status != OrderStatus.COMPLETE) {
            throw new IllegalStateException("只有已收货或已完成的订单才能评价");
        }
    }

    /**
     * 验证配送费是否合法
     */
    public void validateDeliveryFee(BigDecimal deliveryFee) {
        if (deliveryFee == null) {
            return;
        }

        if (deliveryFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("配送费不能为负数");
        }
    }

    /**
     * 验证订单金额的一致性
     */
    public void validateAmountConsistency(BigDecimal amount, BigDecimal payAmount,
                                         BigDecimal discount, BigDecimal pointAmount,
                                         BigDecimal deliveryFee) {
        BigDecimal calculatedPayAmount = amount;

        if (deliveryFee != null) {
            calculatedPayAmount = calculatedPayAmount.add(deliveryFee);
        }

        if (discount != null) {
            calculatedPayAmount = calculatedPayAmount.subtract(discount);
        }

        if (pointAmount != null) {
            calculatedPayAmount = calculatedPayAmount.subtract(pointAmount);
        }

        if (calculatedPayAmount.compareTo(BigDecimal.ZERO) < 0) {
            calculatedPayAmount = BigDecimal.ZERO;
        }

        if (payAmount.compareTo(calculatedPayAmount) != 0) {
            throw new IllegalArgumentException("订单金额计算不一致");
        }
    }
}
