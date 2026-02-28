package cloud.palmbiz.domain.order.repository;

import cloud.palmbiz.domain.order.model.Order;
import cloud.palmbiz.domain.order.model.OrderId;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单仓储接口
 * 定义订单聚合的持久化操作
 *
 * @author DDD Refactoring
 */
public interface OrderRepository {

    /**
     * 根据ID查找订单
     */
    Order findById(OrderId orderId);

    /**
     * 根据订单号查找订单
     */
    Order findByOrderNo(String orderNo);

    /**
     * 保存订单（新增或更新）
     */
    void save(Order order);

    /**
     * 删除订单
     */
    void remove(Order order);

    /**
     * 根据用户ID查找订单列表
     */
    List<Order> findByUserId(Integer userId);

    /**
     * 根据用户ID和状态查找订单列表
     */
    List<Order> findByUserIdAndStatus(Integer userId, String status);

    /**
     * 根据商户ID查找订单列表
     */
    List<Order> findByMerchantId(Integer merchantId);

    /**
     * 根据店铺ID查找订单列表
     */
    List<Order> findByStoreId(Integer storeId);

    /**
     * 分页查询订单
     */
    List<Order> findByPage(Map<String, Object> params, Integer pageNumber, Integer pageSize);

    /**
     * 统计订单数量
     */
    Long countByCondition(Map<String, Object> params);

    /**
     * 根据核销码查找订单
     */
    Order findByVerifyCode(String verifyCode);

    /**
     * 查找待支付的订单
     */
    List<Order> findPendingOrders(Integer userId);

    /**
     * 查找待发货的订单
     */
    List<Order> findPendingDeliveryOrders(Integer merchantId);

    /**
     * 查找待核销的订单
     */
    List<Order> findPendingConfirmOrders(Integer merchantId);

    /**
     * 查找超时未支付的订单
     */
    List<Order> findTimeoutOrders(Date createTimeBefore);

    /**
     * 查找需要自动完成的订单（已收货超过N天）
     */
    List<Order> findAutoCompleteOrders(Date receiveTimeBefore);

    /**
     * 批量更新订单状态
     */
    void batchUpdateStatus(List<Integer> orderIds, String status);

    /**
     * 统计用户订单数量
     */
    Long countByUserId(Integer userId);

    /**
     * 统计用户指定状态的订单数量
     */
    Long countByUserIdAndStatus(Integer userId, String status);

    /**
     * 查询用户最近的订单
     */
    List<Order> findRecentOrdersByUserId(Integer userId, Integer limit);

    /**
     * 根据分佣用户ID查找订单
     */
    List<Order> findByCommissionUserId(Integer commissionUserId);

    /**
     * 查找未计算分佣的订单
     */
    List<Order> findPendingCommissionOrders(Integer merchantId);

    /**
     * 查找未结算的订单
     */
    List<Order> findPendingSettlementOrders(Integer merchantId);

    /**
     * 根据卡券ID查找订单
     */
    List<Order> findByCouponId(Integer couponId);

    /**
     * 统计指定日期范围的订单金额
     */
    Map<String, Object> sumAmountByDateRange(Date startDate, Date endDate, Integer merchantId);

    /**
     * 统计指定日期范围的订单数量
     */
    Long countByDateRange(Date startDate, Date endDate, Integer merchantId);

    /**
     * 查找指定用户在指定商品的订单
     */
    List<Order> findByUserIdAndGoodsId(Integer userId, Integer goodsId);
}
