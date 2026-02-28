package cloud.palmbiz.domain.order.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单商品实体
 * 作为 Order 聚合的一部分
 *
 * @author DDD Refactoring
 */
@Getter
public class OrderGoods {

    /**
     * ID
     */
    private Integer id;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * SKU ID
     */
    private Integer skuId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 折扣价
     */
    private BigDecimal discount;

    /**
     * 数量
     */
    private Double num;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private String status;

    // ==================== 构造方法 ====================

    /**
     * 创建订单商品
     */
    public static OrderGoods create(Integer goodsId, Integer skuId, BigDecimal price,
                                   BigDecimal discount, Double num) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.goodsId = goodsId;
        orderGoods.skuId = skuId;
        orderGoods.price = price;
        orderGoods.discount = discount;
        orderGoods.num = num;
        orderGoods.status = "A";
        Date now = new Date();
        orderGoods.createTime = now;
        orderGoods.updateTime = now;
        return orderGoods;
    }

    /**
     * 从持久化数据重建
     */
    public static OrderGoods reconstitute(Integer id, Integer orderId, Integer goodsId,
                                         Integer skuId, BigDecimal price, BigDecimal discount,
                                         Double num, Date createTime, Date updateTime, String status) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.id = id;
        orderGoods.orderId = orderId;
        orderGoods.goodsId = goodsId;
        orderGoods.skuId = skuId;
        orderGoods.price = price;
        orderGoods.discount = discount;
        orderGoods.num = num;
        orderGoods.createTime = createTime;
        orderGoods.updateTime = updateTime;
        orderGoods.status = status;
        return orderGoods;
    }

    // ==================== 业务方法 ====================

    /**
     * 计算商品总价
     */
    public BigDecimal calculateTotal() {
        BigDecimal actualPrice = discount != null && discount.compareTo(BigDecimal.ZERO) > 0
                ? discount : price;
        return actualPrice.multiply(BigDecimal.valueOf(num));
    }

    /**
     * 是否有折扣
     */
    public boolean hasDiscount() {
        return discount != null && discount.compareTo(BigDecimal.ZERO) > 0
                && discount.compareTo(price) < 0;
    }

    /**
     * 设置订单ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 删除
     */
    public void delete() {
        this.status = "D";
        this.updateTime = new Date();
    }
}
