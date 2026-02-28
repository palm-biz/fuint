package cloud.palmbiz.application.order.service;

import cloud.palmbiz.application.order.query.OrderPageQuery;
import cloud.palmbiz.application.order.query.OrderStatisticsQuery;
import cloud.palmbiz.common.user.dto.UserOrderDto;
import cloud.palmbiz.domain.order.model.Order;
import cloud.palmbiz.domain.order.model.OrderId;
import cloud.palmbiz.domain.order.repository.OrderRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单查询服务
 * 处理订单的查询操作
 *
 * @author DDD Refactoring
 */
@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    /**
     * 根据ID查询订单
     */
    public Order queryById(Integer orderId) throws BusinessCheckException {
        if (orderId == null) {
            throw new BusinessCheckException("订单ID不能为空");
        }

        Order order = orderRepository.findById(OrderId.of(orderId));
        if (order == null) {
            throw new BusinessCheckException("订单不存在");
        }

        return order;
    }

    /**
     * 根据订单号查询订单
     */
    public Order queryByOrderNo(String orderNo) throws BusinessCheckException {
        if (orderNo == null || orderNo.isEmpty()) {
            throw new BusinessCheckException("订单号不能为空");
        }

        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessCheckException("订单不存在");
        }

        return order;
    }

    /**
     * 根据核销码查询订单
     */
    public Order queryByVerifyCode(String verifyCode) throws BusinessCheckException {
        if (verifyCode == null || verifyCode.isEmpty()) {
            throw new BusinessCheckException("核销码不能为空");
        }

        Order order = orderRepository.findByVerifyCode(verifyCode);
        if (order == null) {
            throw new BusinessCheckException("核销码对应的订单不存在");
        }

        return order;
    }

    /**
     * 查询用户订单列表
     */
    public List<Order> queryByUserId(Integer userId) throws BusinessCheckException {
        if (userId == null) {
            throw new BusinessCheckException("用户ID不能为空");
        }

        return orderRepository.findByUserId(userId);
    }

    /**
     * 查询用户指定状态的订单
     */
    public List<Order> queryByUserIdAndStatus(Integer userId, String status) throws BusinessCheckException {
        if (userId == null) {
            throw new BusinessCheckException("用户ID不能为空");
        }

        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    /**
     * 查询商户订单列表
     */
    public List<Order> queryByMerchantId(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findByMerchantId(merchantId);
    }

    /**
     * 查询店铺订单列表
     */
    public List<Order> queryByStoreId(Integer storeId) {
        if (storeId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findByStoreId(storeId);
    }

    /**
     * 分页查询订单
     */
    public PaginationResponse<UserOrderDto> queryByPage(OrderPageQuery query) {
        if (query == null) {
            query = new OrderPageQuery();
        }

        // 构建查询参数
        Map<String, Object> params = buildQueryParams(query);

        // 查询订单列表
        List<Order> orders = orderRepository.findByPage(
                params,
                query.getPageNumber(),
                query.getPageSize()
        );

        // 统计总数
        Long totalCount = orderRepository.countByCondition(params);

        // 转换为DTO
        List<UserOrderDto> orderDtos = orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        // 构建分页响应
        PaginationResponse<UserOrderDto> response = new PaginationResponse<>();
        response.setContent(orderDtos);
        response.setCurrentPage(query.getPageNumber());
        response.setTotalPages((int) Math.ceil((double) totalCount / query.getPageSize()));
        response.setTotalElements(totalCount);

        return response;
    }

    /**
     * 统计用户订单数量
     */
    public Long countByUserId(Integer userId) {
        if (userId == null) {
            return 0L;
        }

        return orderRepository.countByUserId(userId);
    }

    /**
     * 统计用户指定状态的订单数量
     */
    public Long countByUserIdAndStatus(Integer userId, String status) {
        if (userId == null) {
            return 0L;
        }

        return orderRepository.countByUserIdAndStatus(userId, status);
    }

    /**
     * 查询用户最近的订单
     */
    public List<Order> queryRecentOrders(Integer userId, Integer limit) {
        if (userId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findRecentOrdersByUserId(userId, limit);
    }

    /**
     * 查询待支付订单
     */
    public List<Order> queryPendingOrders(Integer userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findPendingOrders(userId);
    }

    /**
     * 查询待发货订单
     */
    public List<Order> queryPendingDeliveryOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findPendingDeliveryOrders(merchantId);
    }

    /**
     * 查询待核销订单
     */
    public List<Order> queryPendingConfirmOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findPendingConfirmOrders(merchantId);
    }

    /**
     * 查询分佣用户的订单
     */
    public List<Order> queryByCommissionUserId(Integer commissionUserId) {
        if (commissionUserId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findByCommissionUserId(commissionUserId);
    }

    /**
     * 查询待计算分佣的订单
     */
    public List<Order> queryPendingCommissionOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findPendingCommissionOrders(merchantId);
    }

    /**
     * 查询待结算订单
     */
    public List<Order> queryPendingSettlementOrders(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findPendingSettlementOrders(merchantId);
    }

    /**
     * 查询用户在指定商品的订单
     */
    public List<Order> queryByUserIdAndGoodsId(Integer userId, Integer goodsId) {
        if (userId == null || goodsId == null) {
            return Collections.emptyList();
        }

        return orderRepository.findByUserIdAndGoodsId(userId, goodsId);
    }

    /**
     * 查询订单统计
     */
    public Map<String, Object> queryStatistics(OrderStatisticsQuery query) {
        Map<String, Object> result = new HashMap<>();

        if (query == null) {
            return result;
        }

        // 这里可以根据不同的统计类型进行统计
        // 暂时返回基础统计
        if (query.getUserId() != null) {
            Long totalCount = orderRepository.countByUserId(query.getUserId());
            result.put("totalCount", totalCount);
        }

        return result;
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 构建查询参数
     */
    private Map<String, Object> buildQueryParams(OrderPageQuery query) {
        Map<String, Object> params = new HashMap<>();

        if (query.getUserId() != null) {
            params.put("userId", query.getUserId());
        }
        if (query.getMerchantId() != null) {
            params.put("merchantId", query.getMerchantId());
        }
        if (query.getStoreId() != null) {
            params.put("storeId", query.getStoreId());
        }
        if (query.getStatus() != null && !query.getStatus().isEmpty()) {
            params.put("status", query.getStatus());
        }
        if (query.getType() != null && !query.getType().isEmpty()) {
            params.put("type", query.getType());
        }
        if (query.getPayStatus() != null && !query.getPayStatus().isEmpty()) {
            params.put("payStatus", query.getPayStatus());
        }
        if (query.getConfirmStatus() != null && !query.getConfirmStatus().isEmpty()) {
            params.put("confirmStatus", query.getConfirmStatus());
        }
        if (query.getSettleStatus() != null && !query.getSettleStatus().isEmpty()) {
            params.put("settleStatus", query.getSettleStatus());
        }
        if (query.getPlatform() != null && !query.getPlatform().isEmpty()) {
            params.put("platform", query.getPlatform());
        }
        if (query.getOrderNo() != null && !query.getOrderNo().isEmpty()) {
            params.put("orderNo", query.getOrderNo());
        }
        if (query.getStartTime() != null) {
            params.put("startTime", query.getStartTime());
        }
        if (query.getEndTime() != null) {
            params.put("endTime", query.getEndTime());
        }

        return params;
    }

    /**
     * 转换为DTO
     */
    private UserOrderDto convertToDto(Order order) {
        if (order == null) {
            return null;
        }

        UserOrderDto dto = new UserOrderDto();
        dto.setId(order.getId());
        dto.setOrderSn(order.getOrderNoValue());
        dto.setType(order.getType());
        dto.setPayType(order.getPayType());
        dto.setAmount(order.getAmount());
        dto.setPayAmount(order.getPayAmount());
        dto.setStatus(order.getStatusCode());
        dto.setRemark(order.getRemark());
        dto.setCreateTime(order.getCreateTime());
        dto.setUpdateTime(order.getUpdateTime());

        return dto;
    }
}
