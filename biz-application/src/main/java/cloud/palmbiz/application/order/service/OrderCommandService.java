package cloud.palmbiz.application.order.service;

import cloud.palmbiz.application.order.command.*;
import cloud.palmbiz.common.util.SeqUtil;
import cloud.palmbiz.domain.order.model.*;
import cloud.palmbiz.domain.order.repository.OrderRepository;
import cloud.palmbiz.domain.order.service.OrderCalculationService;
import cloud.palmbiz.domain.order.service.OrderValidationService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单命令服务
 * 处理订单的创建、修改、删除等命令操作
 *
 * @author DDD Refactoring
 */
@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final OrderCalculationService calculationService;
    private final OrderValidationService validationService;

    /**
     * 创建订单
     */
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(CreateOrderCommand command) throws BusinessCheckException {
        // 1. 验证命令参数
        if (command == null) {
            throw new BusinessCheckException("创建订单命令不能为空");
        }

        // 2. 转换订单商品
        List<OrderGoods> goodsList = convertToOrderGoods(command.getGoodsList());

        // 3. 计算订单金额
        BigDecimal goodsAmount = calculationService.calculateGoodsTotalAmount(goodsList);

        BigDecimal pointAmount = BigDecimal.ZERO;
        if (command.getUsePoint() != null && command.getUsePoint() > 0) {
            // 这里需要获取积分配置，暂时使用简单计算
            // 实际应该调用会员服务获取积分兑换比例
            pointAmount = BigDecimal.valueOf(command.getUsePoint()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN);
        }

        BigDecimal discount = command.getDiscount() != null ? command.getDiscount() : BigDecimal.ZERO;
        BigDecimal deliveryFee = command.getDeliveryFee() != null ? command.getDeliveryFee() : BigDecimal.ZERO;

        BigDecimal payAmount = calculationService.calculatePayAmount(goodsAmount, deliveryFee, discount, pointAmount);

        // 4. 验证订单创建
        validationService.validateOrderCreation(command.getUserId(), goodsList, payAmount);

        // 5. 生成订单号
        String orderSn = SeqUtil.getNextId("order");

        // 6. 创建订单金额对象
        OrderAmount orderAmount = OrderAmount.of(goodsAmount, payAmount, discount, pointAmount, deliveryFee);

        // 7. 创建订单聚合根
        Order order = Order.create(
                orderSn,
                command.getType(),
                command.getUserId(),
                command.getMerchantId(),
                command.getStoreId(),
                orderAmount,
                command.getUsePoint(),
                command.getPlatform(),
                command.getRemark()
        );

        // 8. 设置其他属性
        if (command.getOrderMode() != null) {
            order.setOrderMode(command.getOrderMode());
        }
        if (command.getCouponId() != null) {
            order.setCoupon(command.getCouponId());
        }
        if (command.getParam() != null) {
            order.setParam(command.getParam());
        }
        if (command.getCommissionUserId() != null) {
            order.setCommissionUser(command.getCommissionUserId());
        }

        // 9. 添加订单商品
        order.addGoodsList(goodsList);

        // 10. 保存订单
        orderRepository.save(order);

        return order;
    }

    /**
     * 支付订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(PayOrderCommand command) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(command.getOrderId(), command.getOrderNo());

        // 2. 验证支付
        validationService.validateOrderPayment(order);

        // 3. 执行支付
        order.pay(command.getPayType(), command.getOperator());

        // 4. 保存订单
        orderRepository.save(order);
    }

    /**
     * 取消订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(CancelOrderCommand command) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(command.getOrderId(), command.getOrderNo());

        // 2. 验证取消
        validationService.validateOrderCancellation(order);

        // 3. 执行取消
        order.cancel(command.getOperator());

        // 4. 如果需要退款
        if (Boolean.TRUE.equals(command.getNeedRefund()) && order.isPaid()) {
            // 这里应该调用退款服务
            // refundService.refund(order);
        }

        // 5. 保存订单
        orderRepository.save(order);
    }

    /**
     * 发货
     */
    @Transactional(rollbackFor = Exception.class)
    public void deliverOrder(DeliverOrderCommand command) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(command.getOrderId(), command.getOrderNo());

        // 2. 验证发货
        validationService.validateOrderDelivery(order);

        // 3. 执行发货
        order.deliver(command.getExpressInfo(), command.getOperator());

        // 4. 保存订单
        orderRepository.save(order);
    }

    /**
     * 确认收货
     */
    @Transactional(rollbackFor = Exception.class)
    public void receiveOrder(Integer orderId, String operator) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(orderId, null);

        // 2. 执行收货
        order.receive(operator);

        // 3. 保存订单
        orderRepository.save(order);
    }

    /**
     * 完成订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void completeOrder(Integer orderId, String operator) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(orderId, null);

        // 2. 执行完成
        order.complete(operator);

        // 3. 保存订单
        orderRepository.save(order);
    }

    /**
     * 核销订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(ConfirmOrderCommand command) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(command.getOrderId(), command.getOrderNo());

        // 2. 验证核销
        validationService.validateOrderConfirmation(order, command.getVerifyCode());

        // 3. 执行核销
        order.confirm(command.getConfirmRemark(), command.getStaffId(), command.getOperator());

        // 4. 保存订单
        orderRepository.save(order);
    }

    /**
     * 退款
     */
    @Transactional(rollbackFor = Exception.class)
    public void refundOrder(Integer orderId, String operator) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(orderId, null);

        // 2. 验证退款
        validationService.validateOrderRefund(order);

        // 3. 执行退款
        order.refund(operator);

        // 4. 这里应该调用支付服务进行实际退款
        // paymentService.refund(order);

        // 5. 保存订单
        orderRepository.save(order);
    }

    /**
     * 删除订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Integer orderId, String operator) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(orderId, null);

        // 2. 执行删除
        order.delete(operator);

        // 3. 保存订单
        orderRepository.save(order);
    }

    /**
     * 标记分佣已计算
     */
    @Transactional(rollbackFor = Exception.class)
    public void markCommissionCalculated(Integer orderId, String operator) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(orderId, null);

        // 2. 标记分佣已计算
        order.markCommissionCalculated(operator);

        // 3. 保存订单
        orderRepository.save(order);
    }

    /**
     * 标记已结算
     */
    @Transactional(rollbackFor = Exception.class)
    public void markSettled(Integer orderId, String operator) throws BusinessCheckException {
        // 1. 查找订单
        Order order = findOrder(orderId, null);

        // 2. 标记已结算
        order.markSettled(operator);

        // 3. 保存订单
        orderRepository.save(order);
    }

    /**
     * 批量取消超时订单
     */
    @Transactional(rollbackFor = Exception.class)
    public int cancelTimeoutOrders(java.util.Date createTimeBefore, String operator) {
        List<Order> timeoutOrders = orderRepository.findTimeoutOrders(createTimeBefore);
        int count = 0;

        for (Order order : timeoutOrders) {
            try {
                order.cancel(operator);
                orderRepository.save(order);
                count++;
            } catch (Exception e) {
                // 记录日志，继续处理下一个
            }
        }

        return count;
    }

    /**
     * 批量自动完成订单
     */
    @Transactional(rollbackFor = Exception.class)
    public int autoCompleteOrders(java.util.Date receiveTimeBefore, String operator) {
        List<Order> orders = orderRepository.findAutoCompleteOrders(receiveTimeBefore);
        int count = 0;

        for (Order order : orders) {
            try {
                order.complete(operator);
                orderRepository.save(order);
                count++;
            } catch (Exception e) {
                // 记录日志，继续处理下一个
            }
        }

        return count;
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 查找订单
     */
    private Order findOrder(Integer orderId, String orderNo) throws BusinessCheckException {
        Order order = null;

        if (orderId != null) {
            order = orderRepository.findById(OrderId.of(orderId));
        } else if (orderNo != null && !orderNo.isEmpty()) {
            order = orderRepository.findByOrderNo(orderNo);
        }

        if (order == null) {
            throw new BusinessCheckException("订单不存在");
        }

        return order;
    }

    /**
     * 转换订单商品
     */
    private List<OrderGoods> convertToOrderGoods(List<CreateOrderCommand.OrderGoodsItem> items) {
        List<OrderGoods> goodsList = new ArrayList<>();

        if (items != null && !items.isEmpty()) {
            for (CreateOrderCommand.OrderGoodsItem item : items) {
                OrderGoods goods = OrderGoods.create(
                        item.getGoodsId(),
                        item.getSkuId(),
                        item.getPrice(),
                        item.getDiscount(),
                        item.getNum()
                );
                goodsList.add(goods);
            }
        }

        return goodsList;
    }
}
