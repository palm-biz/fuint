package cloud.palmbiz.domain.cart.model;

import lombok.Getter;

import java.util.Date;

/**
 * 购物车项实体
 * 代表购物车中的一个商品
 *
 * @author DDD Refactoring
 */
@Getter
public class CartItem {

    /**
     * ID
     */
    private Integer id;

    /**
     * 会员ID
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
     * 是否游客
     */
    private String isVisitor;

    /**
     * 挂单号
     */
    private String hangNo;

    /**
     * SKU ID
     */
    private Integer skuId;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 数量
     */
    private CartQuantity quantity;

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
    private CartStatus status;

    // ==================== 构造方法 ====================

    /**
     * 创建购物车项
     */
    public static CartItem create(Integer userId, Integer merchantId, Integer storeId,
                                  Integer goodsId, Integer skuId, Double num) {
        CartItem item = new CartItem();
        item.userId = userId;
        item.merchantId = merchantId;
        item.storeId = storeId;
        item.goodsId = goodsId;
        item.skuId = skuId;
        item.quantity = CartQuantity.of(num);
        item.status = CartStatus.ACTIVE;
        item.isVisitor = "N";
        Date now = new Date();
        item.createTime = now;
        item.updateTime = now;
        return item;
    }

    /**
     * 从持久化数据重建
     */
    public static CartItem reconstitute(Integer id, Integer userId, Integer merchantId,
                                       Integer storeId, String isVisitor, String hangNo,
                                       Integer skuId, Integer goodsId, Double num,
                                       Date createTime, Date updateTime, String status) {
        CartItem item = new CartItem();
        item.id = id;
        item.userId = userId;
        item.merchantId = merchantId;
        item.storeId = storeId;
        item.isVisitor = isVisitor;
        item.hangNo = hangNo;
        item.skuId = skuId;
        item.goodsId = goodsId;
        item.quantity = CartQuantity.of(num);
        item.createTime = createTime;
        item.updateTime = updateTime;
        item.status = CartStatus.fromCode(status);
        return item;
    }

    // ==================== 业务方法 ====================

    /**
     * 增加数量
     */
    public void addQuantity(Double amount) {
        this.quantity = this.quantity.add(amount);
        this.updateTime = new Date();
    }

    /**
     * 减少数量
     */
    public void subtractQuantity(Double amount) {
        this.quantity = this.quantity.subtract(amount);
        this.updateTime = new Date();
    }

    /**
     * 设置数量
     */
    public void setQuantity(Double amount) {
        this.quantity = CartQuantity.of(amount);
        this.updateTime = new Date();
    }

    /**
     * 设置挂单号
     */
    public void setHangNo(String hangNo) {
        this.hangNo = hangNo;
        this.updateTime = new Date();
    }

    /**
     * 设置为游客
     */
    public void markAsVisitor() {
        this.isVisitor = "Y";
        this.updateTime = new Date();
    }

    /**
     * 设置为会员
     */
    public void markAsMember(Integer userId) {
        this.userId = userId;
        this.isVisitor = "N";
        this.updateTime = new Date();
    }

    /**
     * 删除
     */
    public void delete() {
        this.status = CartStatus.DELETED;
        this.updateTime = new Date();
    }

    /**
     * 是否游客购物车
     */
    public boolean isVisitorCart() {
        return "Y".equals(this.isVisitor);
    }

    /**
     * 是否同一商品（goodsId和skuId相同）
     */
    public boolean isSameGoods(Integer goodsId, Integer skuId) {
        if (this.goodsId == null || !this.goodsId.equals(goodsId)) {
            return false;
        }
        if (this.skuId == null && skuId == null) {
            return true;
        }
        return this.skuId != null && this.skuId.equals(skuId);
    }

    /**
     * 获取数量值
     */
    public Double getQuantityValue() {
        return quantity.toDouble();
    }

    /**
     * 获取状态码
     */
    public String getStatusCode() {
        return status.getCode();
    }
}
