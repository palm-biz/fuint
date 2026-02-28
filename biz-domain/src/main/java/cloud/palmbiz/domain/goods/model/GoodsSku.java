package cloud.palmbiz.domain.goods.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品SKU实体
 * 作为 Goods 聚合的一部分
 *
 * @author DDD Refactoring
 */
@Getter
public class GoodsSku {

    /**
     * ID
     */
    private Integer id;

    /**
     * SKU编码
     */
    private String skuNo;

    /**
     * 图片
     */
    private String logo;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 规格ID
     */
    private String specIds;

    /**
     * 库存
     */
    private GoodsStock stock;

    /**
     * 价格
     */
    private GoodsPrice price;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 状态
     */
    private String status;

    // ==================== 构造方法 ====================

    /**
     * 创建SKU
     */
    public static GoodsSku create(String skuNo, Integer goodsId, String specIds,
                                 BigDecimal price, BigDecimal linePrice, BigDecimal costPrice,
                                 Double stock, BigDecimal weight) {
        GoodsSku sku = new GoodsSku();
        sku.skuNo = skuNo;
        sku.goodsId = goodsId;
        sku.specIds = specIds;
        sku.price = GoodsPrice.of(price, linePrice, costPrice);
        sku.stock = GoodsStock.of(stock);
        sku.weight = weight;
        sku.status = "A";
        return sku;
    }

    /**
     * 从持久化数据重建
     */
    public static GoodsSku reconstitute(Integer id, String skuNo, String logo, Integer goodsId,
                                       String specIds, Double stock, BigDecimal price,
                                       BigDecimal linePrice, BigDecimal costPrice,
                                       BigDecimal weight, String status) {
        GoodsSku sku = new GoodsSku();
        sku.id = id;
        sku.skuNo = skuNo;
        sku.logo = logo;
        sku.goodsId = goodsId;
        sku.specIds = specIds;
        sku.stock = GoodsStock.of(stock);
        sku.price = GoodsPrice.of(price, linePrice, costPrice);
        sku.weight = weight;
        sku.status = status;
        return sku;
    }

    // ==================== 业务方法 ====================

    /**
     * 更新价格
     */
    public void updatePrice(BigDecimal price, BigDecimal linePrice, BigDecimal costPrice) {
        this.price = GoodsPrice.of(price, linePrice, costPrice);
    }

    /**
     * 增加库存
     */
    public void addStock(Double amount) {
        this.stock = this.stock.add(amount);
    }

    /**
     * 扣减库存
     */
    public void deductStock(Double amount) {
        this.stock = this.stock.subtract(amount);
    }

    /**
     * 设置库存
     */
    public void setStock(Double stock) {
        this.stock = GoodsStock.of(stock);
    }

    /**
     * 是否有库存
     */
    public boolean hasStock() {
        return stock.hasStock();
    }

    /**
     * 库存是否充足
     */
    public boolean isStockSufficient(Double required) {
        return stock.isSufficient(required);
    }

    /**
     * 设置商品ID
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取价格
     */
    public BigDecimal getPrice() {
        return price.getPrice();
    }

    /**
     * 获取库存
     */
    public Double getStockValue() {
        return stock.toDouble();
    }

    /**
     * 设置图片
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 删除
     */
    public void delete() {
        this.status = "D";
    }

    /**
     * 激活
     */
    public void activate() {
        this.status = "A";
    }
}
