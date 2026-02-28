package cloud.palmbiz.application.order.command;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单命令
 *
 * @author DDD Refactoring
 */
@Data
public class CreateOrderCommand {

    /**
     * 订单类型
     */
    private String type;

    /**
     * 订单模式
     */
    private String orderMode;

    /**
     * 下单平台
     */
    private String platform;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 卡券ID
     */
    private Integer couponId;

    /**
     * 订单商品列表
     */
    private List<OrderGoodsItem> goodsList;

    /**
     * 使用积分
     */
    private Integer usePoint;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     * 折扣金额
     */
    private BigDecimal discount;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 订单参数（JSON）
     */
    private String param;

    /**
     * 分佣用户ID
     */
    private Integer commissionUserId;

    /**
     * 是否游客
     */
    private String isVisitor;

    /**
     * 订单商品项
     */
    @Data
    public static class OrderGoodsItem {
        /**
         * 商品ID
         */
        private Integer goodsId;

        /**
         * SKU ID
         */
        private Integer skuId;

        /**
         * 商品价格
         */
        private BigDecimal price;

        /**
         * 折扣价
         */
        private BigDecimal discount;

        /**
         * 购买数量
         */
        private Double num;
    }
}
