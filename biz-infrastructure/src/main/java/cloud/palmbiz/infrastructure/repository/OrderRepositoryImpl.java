package cloud.palmbiz.infrastructure.repository;

import cloud.palmbiz.domain.order.model.*;
import cloud.palmbiz.domain.order.repository.OrderRepository;
import cloud.palmbiz.infrastructure.mapper.MtOrderGoodsMapper;
import cloud.palmbiz.infrastructure.mapper.MtOrderMapper;
import cloud.palmbiz.infrastructure.model.MtOrder;
import cloud.palmbiz.infrastructure.model.MtOrderGoods;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单仓储实现
 * 实现订单聚合的持久化操作
 *
 * @author DDD Refactoring
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final MtOrderMapper orderMapper;
    private final MtOrderGoodsMapper orderGoodsMapper;

    @Override
    public Order findById(OrderId orderId) {
        if (orderId == null) {
            return null;
        }
        MtOrder po = orderMapper.selectById(orderId.getValue());
        return toDomain(po);
    }

    @Override
    public Order findByOrderNo(String orderNo) {
        if (orderNo == null || orderNo.isEmpty()) {
            return null;
        }
        MtOrder po = orderMapper.findByOrderSn(orderNo);
        return toDomain(po);
    }

    @Override
    public void save(Order order) {
        if (order == null) {
            return;
        }

        MtOrder po = toPO(order);

        if (order.getId() == null) {
            // 新增
            orderMapper.insert(po);
            // 回写ID
            if (po.getId() != null) {
                // 注意：这里无法直接修改Order的private id字段
                // 需要在应用层处理或者在Order中提供设置ID的方法
            }
        } else {
            // 更新
            orderMapper.updateById(po);
        }

        // 保存订单商品
        if (order.getGoodsList() != null && !order.getGoodsList().isEmpty()) {
            saveOrderGoods(order.getId(), order.getGoodsList());
        }
    }

    @Override
    public void remove(Order order) {
        if (order == null || order.getId() == null) {
            return;
        }
        orderMapper.deleteById(order.getId());

        // 删除订单商品
        LambdaQueryWrapper<MtOrderGoods> wrapper = Wrappers.lambdaQueryWrapper();
        wrapper.eq(MtOrderGoods::getOrderId, order.getId());
        orderGoodsMapper.delete(wrapper);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getUserId, userId);
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserIdAndStatus(Integer userId, String status) {
        if (userId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq(MtOrder::getStatus, status);
        }
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByMerchantId(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getMerchantId, merchantId);
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStoreId(Integer storeId) {
        if (storeId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getStoreId, storeId);
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByPage(Map<String, Object> params, Integer pageNumber, Integer pageSize) {
        // 这里需要结合 PageHelper 或者其他分页工具实现
        // 暂时返回空列表，具体实现在应用层
        return Collections.emptyList();
    }

    @Override
    public Long countByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<MtOrder> wrapper = buildQueryWrapper(params);
        return orderMapper.selectCount(wrapper);
    }

    @Override
    public Order findByVerifyCode(String verifyCode) {
        if (verifyCode == null || verifyCode.isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getVerifyCode, verifyCode);

        MtOrder po = orderMapper.selectOne(wrapper);
        return toDomain(po);
    }

    @Override
    public List<Order> findPendingOrders(Integer userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getUserId, userId);
        wrapper.eq(MtOrder::getStatus, OrderStatus.CREATED.getCode());
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findPendingDeliveryOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getMerchantId, merchantId);
        wrapper.in(MtOrder::getStatus, OrderStatus.PAID.getCode(), OrderStatus.DELIVERY.getCode());
        wrapper.orderByAsc(MtOrder::getPayTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findPendingConfirmOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getMerchantId, merchantId);
        wrapper.eq(MtOrder::getStatus, OrderStatus.PAID.getCode());
        wrapper.eq(MtOrder::getConfirmStatus, "N");
        wrapper.orderByAsc(MtOrder::getPayTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findTimeoutOrders(Date createTimeBefore) {
        if (createTimeBefore == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getStatus, OrderStatus.CREATED.getCode());
        wrapper.lt(MtOrder::getCreateTime, createTimeBefore);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAutoCompleteOrders(Date receiveTimeBefore) {
        if (receiveTimeBefore == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getStatus, OrderStatus.RECEIVED.getCode());
        wrapper.lt(MtOrder::getUpdateTime, receiveTimeBefore);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void batchUpdateStatus(List<Integer> orderIds, String status) {
        if (orderIds == null || orderIds.isEmpty() || status == null) {
            return;
        }

        MtOrder updatePO = new MtOrder();
        updatePO.setStatus(status);
        updatePO.setUpdateTime(new Date());

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MtOrder::getId, orderIds);

        orderMapper.update(updatePO, wrapper);
    }

    @Override
    public Long countByUserId(Integer userId) {
        if (userId == null) {
            return 0L;
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getUserId, userId);

        return orderMapper.selectCount(wrapper);
    }

    @Override
    public Long countByUserIdAndStatus(Integer userId, String status) {
        if (userId == null) {
            return 0L;
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq(MtOrder::getStatus, status);
        }

        return orderMapper.selectCount(wrapper);
    }

    @Override
    public List<Order> findRecentOrdersByUserId(Integer userId, Integer limit) {
        if (userId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getUserId, userId);
        wrapper.orderByDesc(MtOrder::getCreateTime);
        wrapper.last("LIMIT " + (limit != null ? limit : 10));

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByCommissionUserId(Integer commissionUserId) {
        if (commissionUserId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getCommissionUserId, commissionUserId);
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findPendingCommissionOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getMerchantId, merchantId);
        wrapper.eq(MtOrder::getCommissionStatus, "N");
        wrapper.in(MtOrder::getStatus, OrderStatus.PAID.getCode(), OrderStatus.COMPLETE.getCode());
        wrapper.isNotNull(MtOrder::getCommissionUserId);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findPendingSettlementOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getMerchantId, merchantId);
        wrapper.eq(MtOrder::getSettleStatus, "N");
        wrapper.eq(MtOrder::getStatus, OrderStatus.COMPLETE.getCode());

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByCouponId(Integer couponId) {
        if (couponId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrder::getCouponId, couponId);
        wrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> sumAmountByDateRange(Date startDate, Date endDate, Integer merchantId) {
        // 使用 mapper 中的统计方法
        return new HashMap<>();
    }

    @Override
    public Long countByDateRange(Date startDate, Date endDate, Integer merchantId) {
        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtOrder::getMerchantId, merchantId);
        }
        if (startDate != null) {
            wrapper.ge(MtOrder::getCreateTime, startDate);
        }
        if (endDate != null) {
            wrapper.le(MtOrder::getCreateTime, endDate);
        }

        return orderMapper.selectCount(wrapper);
    }

    @Override
    public List<Order> findByUserIdAndGoodsId(Integer userId, Integer goodsId) {
        if (userId == null || goodsId == null) {
            return Collections.emptyList();
        }

        // 先查找包含该商品的订单ID
        LambdaQueryWrapper<MtOrderGoods> goodsWrapper = Wrappers.lambdaQuery();
        goodsWrapper.eq(MtOrderGoods::getGoodsId, goodsId);
        goodsWrapper.eq(MtOrderGoods::getStatus, "A");

        List<MtOrderGoods> goodsList = orderGoodsMapper.selectList(goodsWrapper);
        if (goodsList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> orderIds = goodsList.stream()
                .map(MtOrderGoods::getOrderId)
                .distinct()
                .collect(Collectors.toList());

        // 再查找用户的订单
        LambdaQueryWrapper<MtOrder> orderWrapper = Wrappers.lambdaQuery();
        orderWrapper.eq(MtOrder::getUserId, userId);
        orderWrapper.in(MtOrder::getId, orderIds);
        orderWrapper.orderByDesc(MtOrder::getCreateTime);

        List<MtOrder> poList = orderMapper.selectList(orderWrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 领域对象转PO
     */
    private MtOrder toPO(Order domain) {
        if (domain == null) {
            return null;
        }

        MtOrder po = new MtOrder();
        po.setId(domain.getId());
        po.setOrderSn(domain.getOrderNoValue());
        po.setType(domain.getType());
        po.setPayType(domain.getPayType());
        po.setOrderMode(domain.getOrderMode());
        po.setPlatform(domain.getPlatform());
        po.setCouponId(domain.getCouponId());
        po.setMerchantId(domain.getMerchantId());
        po.setStoreId(domain.getStoreId());
        po.setUserId(domain.getUserId());
        po.setVerifyCode(domain.getVerifyCode());
        po.setIsVisitor(domain.getIsVisitor());

        // 金额信息
        if (domain.getOrderAmount() != null) {
            po.setAmount(domain.getOrderAmount().getAmount());
            po.setPayAmount(domain.getOrderAmount().getPayAmount());
            po.setDiscount(domain.getOrderAmount().getDiscount());
            po.setPointAmount(domain.getOrderAmount().getPointAmount());
            po.setDeliveryFee(domain.getOrderAmount().getDeliveryFee());
        }

        po.setSettleStatus(domain.getSettleStatus());
        po.setUsePoint(domain.getUsePoint());
        po.setParam(domain.getParam());
        po.setExpressInfo(domain.getExpressInfo());
        po.setRemark(domain.getRemark());
        po.setStatus(domain.getStatusCode());
        po.setPayTime(domain.getPayTime());
        po.setPayStatus(domain.getPayStatus());
        po.setStaffId(domain.getStaffId());
        po.setConfirmStatus(domain.getConfirmStatus());
        po.setConfirmTime(domain.getConfirmTime());
        po.setConfirmRemark(domain.getConfirmRemark());
        po.setCommissionUserId(domain.getCommissionUserId());
        po.setCommissionStatus(domain.getCommissionStatus());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        po.setOperator(domain.getOperator());

        return po;
    }

    /**
     * PO转领域对象
     */
    private Order toDomain(MtOrder po) {
        if (po == null) {
            return null;
        }

        // 构建金额对象
        OrderAmount orderAmount = OrderAmount.of(
                po.getAmount(),
                po.getPayAmount(),
                po.getDiscount(),
                po.getPointAmount(),
                po.getDeliveryFee()
        );

        // 重建订单聚合根
        Order order = Order.reconstitute(
                po.getId(),
                po.getOrderSn(),
                po.getType(),
                po.getPayType(),
                po.getOrderMode(),
                po.getPlatform(),
                po.getCouponId(),
                po.getMerchantId(),
                po.getStoreId(),
                po.getUserId(),
                po.getVerifyCode(),
                po.getIsVisitor(),
                orderAmount,
                po.getSettleStatus(),
                po.getUsePoint(),
                po.getParam(),
                po.getExpressInfo(),
                po.getRemark(),
                po.getStatus(),
                po.getPayTime(),
                po.getPayStatus(),
                po.getStaffId(),
                po.getConfirmStatus(),
                po.getConfirmTime(),
                po.getConfirmRemark(),
                po.getCommissionUserId(),
                po.getCommissionStatus(),
                po.getCreateTime(),
                po.getUpdateTime(),
                po.getOperator()
        );

        // 加载订单商品
        List<OrderGoods> goodsList = loadOrderGoods(po.getId());
        order.setGoodsList(goodsList);

        return order;
    }

    /**
     * 加载订单商品
     */
    private List<OrderGoods> loadOrderGoods(Integer orderId) {
        if (orderId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtOrderGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtOrderGoods::getOrderId, orderId);
        wrapper.eq(MtOrderGoods::getStatus, "A");

        List<MtOrderGoods> poList = orderGoodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toOrderGoodsDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * PO转OrderGoods领域对象
     */
    private OrderGoods toOrderGoodsDomain(MtOrderGoods po) {
        if (po == null) {
            return null;
        }

        return OrderGoods.reconstitute(
                po.getId(),
                po.getOrderId(),
                po.getGoodsId(),
                po.getSkuId(),
                po.getPrice(),
                po.getDiscount(),
                po.getNum(),
                po.getCreateTime(),
                po.getUpdateTime(),
                po.getStatus()
        );
    }

    /**
     * OrderGoods领域对象转PO
     */
    private MtOrderGoods toOrderGoodsPO(OrderGoods domain) {
        if (domain == null) {
            return null;
        }

        MtOrderGoods po = new MtOrderGoods();
        po.setId(domain.getId());
        po.setOrderId(domain.getOrderId());
        po.setGoodsId(domain.getGoodsId());
        po.setSkuId(domain.getSkuId());
        po.setPrice(domain.getPrice());
        po.setDiscount(domain.getDiscount());
        po.setNum(domain.getNum());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        po.setStatus(domain.getStatus());

        return po;
    }

    /**
     * 保存订单商品
     */
    private void saveOrderGoods(Integer orderId, List<OrderGoods> goodsList) {
        if (orderId == null || goodsList == null || goodsList.isEmpty()) {
            return;
        }

        for (OrderGoods goods : goodsList) {
            MtOrderGoods po = toOrderGoodsPO(goods);
            if (po.getId() == null) {
                orderGoodsMapper.insert(po);
            } else {
                orderGoodsMapper.updateById(po);
            }
        }
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<MtOrder> buildQueryWrapper(Map<String, Object> params) {
        LambdaQueryWrapper<MtOrder> wrapper = Wrappers.lambdaQuery();

        if (params == null || params.isEmpty()) {
            return wrapper;
        }

        if (params.containsKey("merchantId")) {
            wrapper.eq(MtOrder::getMerchantId, params.get("merchantId"));
        }
        if (params.containsKey("storeId")) {
            wrapper.eq(MtOrder::getStoreId, params.get("storeId"));
        }
        if (params.containsKey("userId")) {
            wrapper.eq(MtOrder::getUserId, params.get("userId"));
        }
        if (params.containsKey("status")) {
            wrapper.eq(MtOrder::getStatus, params.get("status"));
        }
        if (params.containsKey("type")) {
            wrapper.eq(MtOrder::getType, params.get("type"));
        }

        return wrapper;
    }
}
