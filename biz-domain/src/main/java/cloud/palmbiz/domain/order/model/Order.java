package cloud.palmbiz.domain.order.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单聚合根
 * 封装订单的核心业务逻辑和状态管理
 *
 * @author DDD Refactoring
 */
@Getter
public class Order {

    /**
     * 订单ID
     */
    private Integer id;

    /**
     * 订单号
     */
    private OrderNo orderNo;

    /**
     * 订单类型
     */
    private String type;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 订单模式
     */
    private String orderMode;

    /**
     * 下单平台
     */
    private String platform;

    /**
     * 卡券ID
     */
    private Integer couponId;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 核销验证码
     */
    private String verifyCode;

    /**
     * 是否游客
     */
    private String isVisitor;

    /**
     * 订单金额
     */
    private OrderAmount orderAmount;

    /**
     * 结算状态
     */
    private String settleStatus;

    /**
     * 使用积分数量
     */
    private Integer usePoint;

    /**
     * 订单参数
     */
    private String param;

    /**
     * 物流信息
     */
    private String expressInfo;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 订单状态
     */
    private OrderStatus status;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 操作员工ID
     */
    private Integer staffId;

    /**
     * 核销状态
     */
    private String confirmStatus;

    /**
     * 核销时间
     */
    private Date confirmTime;

    /**
     * 核销备注
     */
    private String confirmRemark;

    /**
     * 分佣提成用户ID
     */
    private Integer commissionUserId;

    /**
     * 分佣提成计算状态
     */
    private String commissionStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 最后操作人
     */
    private String operator;

    /**
     * 订单商品列表（聚合内部实体）
     */
    private List<OrderGoods> goodsList;

    // ==================== 构造方法 ====================

    private Order() {
        this.goodsList = new ArrayList<>();
    }

    /**
     * 创建新订单
     */
    public static Order create(String orderSn, String type, Integer userId, Integer merchantId,
                              Integer storeId, OrderAmount orderAmount, Integer usePoint,
                              String platform, String remark) {
        Order order = new Order();
        order.orderNo = OrderNo.of(orderSn);
        order.type = type;
        order.userId = userId;
        order.merchantId = merchantId;
        order.storeId = storeId;
        order.orderAmount = orderAmount;
        order.usePoint = usePoint != null ? usePoint : 0;
        order.platform = platform;
        order.remark = remark;
        order.status = OrderStatus.CREATED;
        order.payStatus = "N";
        order.confirmStatus = "N";
        order.settleStatus = "N";
        order.commissionStatus = "N";
        order.isVisitor = "N";
        Date now = new Date();
        order.createTime = now;
        order.updateTime = now;
        return order;
    }

    /**
     * 从持久化数据重建
     */
    public static Order reconstitute(Integer id, String orderSn, String type, String payType,
                                    String orderMode, String platform, Integer couponId,
                                    Integer merchantId, Integer storeId, Integer userId,
                                    String verifyCode, String isVisitor, OrderAmount orderAmount,
                                    String settleStatus, Integer usePoint, String param,
                                    String expressInfo, String remark, String status,
                                    Date payTime, String payStatus, Integer staffId,
                                    String confirmStatus, Date confirmTime, String confirmRemark,
                                    Integer commissionUserId, String commissionStatus,
                                    Date createTime, Date updateTime, String operator) {
        Order order = new Order();
        order.id = id;
        order.orderNo = OrderNo.of(orderSn);
        order.type = type;
        order.payType = payType;
        order.orderMode = orderMode;
        order.platform = platform;
        order.couponId = couponId;
        order.merchantId = merchantId;
        order.storeId = storeId;
        order.userId = userId;
        order.verifyCode = verifyCode;
        order.isVisitor = isVisitor;
        order.orderAmount = orderAmount;
        order.settleStatus = settleStatus;
        order.usePoint = usePoint;
        order.param = param;
        order.expressInfo = expressInfo;
        order.remark = remark;
        order.status = OrderStatus.fromCode(status);
        order.payTime = payTime;
        order.payStatus = payStatus;
        order.staffId = staffId;
        order.confirmStatus = confirmStatus;
        order.confirmTime = confirmTime;
        order.confirmRemark = confirmRemark;
        order.commissionUserId = commissionUserId;
        order.commissionStatus = commissionStatus;
        order.createTime = createTime;
        order.updateTime = updateTime;
        order.operator = operator;
        return order;
    }

    // ==================== 聚合内实体管理 ====================

    /**
     * 添加订单商品
     */
    public void addGoods(OrderGoods goods) {
        if (goods == null) {
            throw new IllegalArgumentException("订单商品不能为空");
        }
        goods.setOrderId(this.id);
        this.goodsList.add(goods);
        this.updateTime = new Date();
    }

    /**
     * 批量添加订单商品
     */
    public void addGoodsList(List<OrderGoods> goodsList) {
        if (goodsList != null && !goodsList.isEmpty()) {
            for (OrderGoods goods : goodsList) {
                addGoods(goods);
            }
        }
    }

    /**
     * 设置订单商品列表
     */
    public void setGoodsList(List<OrderGoods> goodsList) {
        this.goodsList = goodsList != null ? goodsList : new ArrayList<>();
        if (!this.goodsList.isEmpty() && this.id != null) {
            for (OrderGoods goods : this.goodsList) {
                goods.setOrderId(this.id);
            }
        }
    }

    // ==================== 订单状态转换 ====================

    /**
     * 支付订单
     */
    public void pay(String payType, String operator) {
        if (!status.canPay()) {
            throw new IllegalStateException("当前订单状态不允许支付");
        }
        if (!orderAmount.validatePayAmount()) {
            throw new IllegalStateException("实付金额验证失败");
        }
        this.status = OrderStatus.PAID;
        this.payType = payType;
        this.payStatus = "Y";
        this.payTime = new Date();
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 取消订单
     */
    public void cancel(String operator) {
        if (!status.canCancel()) {
            throw new IllegalStateException("当前订单状态不允许取消");
        }
        this.status = OrderStatus.CANCEL;
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 发货
     */
    public void deliver(String expressInfo, String operator) {
        if (!status.canDeliver()) {
            throw new IllegalStateException("当前订单状态不允许发货");
        }
        this.status = OrderStatus.DELIVERED;
        this.expressInfo = expressInfo;
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 收货
     */
    public void receive(String operator) {
        if (!status.canReceive()) {
            throw new IllegalStateException("当前订单状态不允许收货");
        }
        this.status = OrderStatus.RECEIVED;
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 完成订单
     */
    public void complete(String operator) {
        if (!status.canComplete()) {
            throw new IllegalStateException("当前订单状态不允许完成");
        }
        this.status = OrderStatus.COMPLETE;
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 退款
     */
    public void refund(String operator) {
        if (!status.canRefund()) {
            throw new IllegalStateException("当前订单状态不允许退款");
        }
        this.status = OrderStatus.REFUND;
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 删除订单
     */
    public void delete(String operator) {
        if (status.isFinal() && status != OrderStatus.CANCEL) {
            throw new IllegalStateException("已完成或已退款的订单不能删除");
        }
        this.status = OrderStatus.DELETED;
        this.updateTime = new Date();
        this.operator = operator;
    }

    // ==================== 核销相关 ====================

    /**
     * 核销订单
     */
    public void confirm(String confirmRemark, Integer staffId, String operator) {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("只有已支付的订单才能核销");
        }
        if (!"N".equals(this.confirmStatus)) {
            throw new IllegalStateException("订单已核销，不能重复核销");
        }
        this.confirmStatus = "Y";
        this.confirmTime = new Date();
        this.confirmRemark = confirmRemark;
        this.staffId = staffId;
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 是否已核销
     */
    public boolean isConfirmed() {
        return "Y".equals(this.confirmStatus);
    }

    /**
     * 生成核销验证码
     */
    public void generateVerifyCode(String code) {
        this.verifyCode = code;
        this.updateTime = new Date();
    }

    // ==================== 分佣相关 ====================

    /**
     * 设置分佣用户
     */
    public void setCommissionUser(Integer commissionUserId) {
        this.commissionUserId = commissionUserId;
        this.updateTime = new Date();
    }

    /**
     * 标记分佣已计算
     */
    public void markCommissionCalculated(String operator) {
        this.commissionStatus = "Y";
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 是否已计算分佣
     */
    public boolean isCommissionCalculated() {
        return "Y".equals(this.commissionStatus);
    }

    // ==================== 结算相关 ====================

    /**
     * 标记已结算
     */
    public void markSettled(String operator) {
        this.settleStatus = "Y";
        this.updateTime = new Date();
        this.operator = operator;
    }

    /**
     * 是否已结算
     */
    public boolean isSettled() {
        return "Y".equals(this.settleStatus);
    }

    // ==================== 金额计算 ====================

    /**
     * 计算订单商品总金额
     */
    public BigDecimal calculateGoodsTotalAmount() {
        if (goodsList == null || goodsList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return goodsList.stream()
                .map(OrderGoods::calculateTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 应用折扣
     */
    public void applyDiscount(BigDecimal discount) {
        if (discount == null || discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("折扣金额不能为负数");
        }
        BigDecimal currentAmount = this.orderAmount.getAmount();
        BigDecimal currentPayAmount = this.orderAmount.getPayAmount();
        BigDecimal pointAmount = this.orderAmount.getPointAmount();
        BigDecimal deliveryFee = this.orderAmount.getDeliveryFee();

        this.orderAmount = OrderAmount.of(currentAmount, currentPayAmount, discount, pointAmount, deliveryFee);
        this.updateTime = new Date();
    }

    /**
     * 应用积分抵扣
     */
    public void applyPointDeduction(Integer points, BigDecimal pointAmount) {
        if (points == null || points < 0) {
            throw new IllegalArgumentException("积分数量不能为负数");
        }
        if (pointAmount == null || pointAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("积分金额不能为负数");
        }

        BigDecimal currentAmount = this.orderAmount.getAmount();
        BigDecimal currentPayAmount = this.orderAmount.getPayAmount();
        BigDecimal discount = this.orderAmount.getDiscount();
        BigDecimal deliveryFee = this.orderAmount.getDeliveryFee();

        this.usePoint = points;
        this.orderAmount = OrderAmount.of(currentAmount, currentPayAmount, discount, pointAmount, deliveryFee);
        this.updateTime = new Date();
    }

    /**
     * 设置配送费
     */
    public void setDeliveryFee(BigDecimal deliveryFee) {
        if (deliveryFee == null || deliveryFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("配送费不能为负数");
        }

        BigDecimal currentAmount = this.orderAmount.getAmount();
        BigDecimal currentPayAmount = this.orderAmount.getPayAmount();
        BigDecimal discount = this.orderAmount.getDiscount();
        BigDecimal pointAmount = this.orderAmount.getPointAmount();

        this.orderAmount = OrderAmount.of(currentAmount, currentPayAmount, discount, pointAmount, deliveryFee);
        this.updateTime = new Date();
    }

    // ==================== 业务查询方法 ====================

    /**
     * 是否已支付
     */
    public boolean isPaid() {
        return status.isPaid();
    }

    /**
     * 是否为终态
     */
    public boolean isFinal() {
        return status.isFinal();
    }

    /**
     * 是否需要发货
     */
    public boolean needsDelivery() {
        // 预留：根据订单类型判断是否需要发货
        return !"writeOff".equals(this.orderMode);
    }

    /**
     * 是否可以核销
     */
    public boolean canConfirm() {
        return status == OrderStatus.PAID && "N".equals(this.confirmStatus);
    }

    /**
     * 是否为游客订单
     */
    public boolean isVisitorOrder() {
        return "Y".equals(this.isVisitor);
    }

    /**
     * 获取订单实付金额
     */
    public BigDecimal getPayAmount() {
        return orderAmount.getPayAmount();
    }

    /**
     * 获取订单原始金额
     */
    public BigDecimal getAmount() {
        return orderAmount.getAmount();
    }

    /**
     * 设置订单参数
     */
    public void setParam(String param) {
        this.param = param;
        this.updateTime = new Date();
    }

    /**
     * 设置订单模式
     */
    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
        this.updateTime = new Date();
    }

    /**
     * 设置卡券
     */
    public void setCoupon(Integer couponId) {
        this.couponId = couponId;
        this.updateTime = new Date();
    }

    /**
     * 获取订单号字符串
     */
    public String getOrderNoValue() {
        return orderNo.getValue();
    }

    /**
     * 获取状态码
     */
    public String getStatusCode() {
        return status.getCode();
    }
}
