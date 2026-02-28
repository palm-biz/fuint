package cloud.palmbiz.domain.goods.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品聚合根
 * 封装商品的核心业务逻辑
 *
 * @author DDD Refactoring
 */
@Getter
public class Goods {

    /**
     * 商品ID
     */
    private Integer id;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品类型
     */
    private String type;

    /**
     * 分类ID
     */
    private Integer cateId;

    /**
     * 预约项目ID
     */
    private Integer bookId;

    /**
     * 商品编码
     */
    private GoodsNo goodsNo;

    /**
     * 可用平台
     */
    private Integer platform;

    /**
     * 是否单规格
     */
    private String isSingleSpec;

    /**
     * 主图地址
     */
    private String logo;

    /**
     * 图片地址
     */
    private String images;

    /**
     * 价格信息
     */
    private GoodsPrice price;

    /**
     * 库存
     */
    private GoodsStock stock;

    /**
     * 关联卡券
     */
    private String couponIds;

    /**
     * 服务时长
     */
    private Integer serviceTime;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 初始销量
     */
    private Double initSale;

    /**
     * 商品卖点
     */
    private String salePoint;

    /**
     * 可否使用积分抵扣
     */
    private String canUsePoint;

    /**
     * 会员是否有折扣
     */
    private String isMemberDiscount;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 商品描述
     */
    private String description;

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
     * 状态
     */
    private GoodsStatus status;

    /**
     * SKU列表（聚合内部实体）
     */
    private List<GoodsSku> skuList;

    // ==================== 构造方法 ====================

    private Goods() {
        this.skuList = new ArrayList<>();
    }

    /**
     * 创建新商品
     */
    public static Goods create(String name, String type, Integer merchantId, Integer storeId,
                              Integer cateId, String goodsNo, BigDecimal price,
                              BigDecimal linePrice, BigDecimal costPrice, Double stock) {
        Goods goods = new Goods();
        goods.name = name;
        goods.type = type;
        goods.merchantId = merchantId;
        goods.storeId = storeId;
        goods.cateId = cateId;
        goods.goodsNo = GoodsNo.of(goodsNo);
        goods.price = GoodsPrice.of(price, linePrice, costPrice);
        goods.stock = GoodsStock.of(stock);
        goods.status = GoodsStatus.ACTIVE;
        goods.isSingleSpec = "Y";
        goods.canUsePoint = "Y";
        goods.isMemberDiscount = "Y";
        goods.platform = 0;
        goods.sort = 0;
        goods.initSale = 0.0;
        Date now = new Date();
        goods.createTime = now;
        goods.updateTime = now;
        return goods;
    }

    /**
     * 从持久化数据重建
     */
    public static Goods reconstitute(Integer id, Integer merchantId, Integer storeId, String name,
                                    String type, Integer cateId, Integer bookId, String goodsNo,
                                    Integer platform, String isSingleSpec, String logo, String images,
                                    BigDecimal price, BigDecimal linePrice, BigDecimal costPrice,
                                    Double stock, String couponIds, Integer serviceTime,
                                    BigDecimal weight, Double initSale, String salePoint,
                                    String canUsePoint, String isMemberDiscount, Integer sort,
                                    String description, Date createTime, Date updateTime,
                                    String operator, String status) {
        Goods goods = new Goods();
        goods.id = id;
        goods.merchantId = merchantId;
        goods.storeId = storeId;
        goods.name = name;
        goods.type = type;
        goods.cateId = cateId;
        goods.bookId = bookId;
        goods.goodsNo = goodsNo != null ? GoodsNo.of(goodsNo) : null;
        goods.platform = platform;
        goods.isSingleSpec = isSingleSpec;
        goods.logo = logo;
        goods.images = images;
        goods.price = GoodsPrice.of(price, linePrice, costPrice);
        goods.stock = GoodsStock.of(stock);
        goods.couponIds = couponIds;
        goods.serviceTime = serviceTime;
        goods.weight = weight;
        goods.initSale = initSale;
        goods.salePoint = salePoint;
        goods.canUsePoint = canUsePoint;
        goods.isMemberDiscount = isMemberDiscount;
        goods.sort = sort;
        goods.description = description;
        goods.createTime = createTime;
        goods.updateTime = updateTime;
        goods.operator = operator;
        goods.status = GoodsStatus.fromCode(status);
        return goods;
    }

    // ==================== 聚合内实体管理 ====================

    /**
     * 添加SKU
     */
    public void addSku(GoodsSku sku) {
        if (sku == null) {
            throw new IllegalArgumentException("SKU不能为空");
        }
        sku.setGoodsId(this.id);
        this.skuList.add(sku);
        this.updateTime = new Date();
    }

    /**
     * 批量添加SKU
     */
    public void addSkuList(List<GoodsSku> skuList) {
        if (skuList != null && !skuList.isEmpty()) {
            for (GoodsSku sku : skuList) {
                addSku(sku);
            }
        }
    }

    /**
     * 设置SKU列表
     */
    public void setSkuList(List<GoodsSku> skuList) {
        this.skuList = skuList != null ? skuList : new ArrayList<>();
        if (!this.skuList.isEmpty() && this.id != null) {
            for (GoodsSku sku : this.skuList) {
                sku.setGoodsId(this.id);
            }
        }
    }

    /**
     * 是否为单规格商品
     */
    public boolean isSingleSpec() {
        return "Y".equals(this.isSingleSpec);
    }

    // ==================== 价格管理 ====================

    /**
     * 更新价格
     */
    public void updatePrice(BigDecimal price, BigDecimal linePrice, BigDecimal costPrice) {
        this.price = GoodsPrice.of(price, linePrice, costPrice);
        this.updateTime = new Date();
    }

    /**
     * 应用折扣
     */
    public void applyDiscount(BigDecimal discountRate) {
        this.price = this.price.applyDiscount(discountRate);
        this.updateTime = new Date();
    }

    /**
     * 获取销售价
     */
    public BigDecimal getPrice() {
        return price.getPrice();
    }

    // ==================== 库存管理 ====================

    /**
     * 增加库存
     */
    public void addStock(Double amount) {
        this.stock = this.stock.add(amount);
        this.updateTime = new Date();
    }

    /**
     * 扣减库存
     */
    public void deductStock(Double amount) {
        this.stock = this.stock.subtract(amount);
        this.updateTime = new Date();
    }

    /**
     * 设置库存
     */
    public void setStock(Double stock) {
        this.stock = GoodsStock.of(stock);
        this.updateTime = new Date();
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
     * 获取库存值
     */
    public Double getStockValue() {
        return stock.toDouble();
    }

    // ==================== 销量管理 ====================

    /**
     * 增加销量
     */
    public void addSale(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("销量增加数量必须大于0");
        }
        this.initSale = (this.initSale != null ? this.initSale : 0.0) + amount;
        this.updateTime = new Date();
    }

    /**
     * 设置初始销量
     */
    public void setInitSale(Double initSale) {
        if (initSale == null || initSale < 0) {
            throw new IllegalArgumentException("初始销量不能为负数");
        }
        this.initSale = initSale;
        this.updateTime = new Date();
    }

    // ==================== 状态管理 ====================

    /**
     * 上架
     */
    public void activate(String operator) {
        this.status = GoodsStatus.ACTIVE;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 下架
     */
    public void deactivate(String operator) {
        this.status = GoodsStatus.INACTIVE;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 删除
     */
    public void delete(String operator) {
        this.status = GoodsStatus.DELETED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 是否可售
     */
    public boolean isAvailable() {
        return status.isAvailable() && hasStock();
    }

    /**
     * 是否已删除
     */
    public boolean isDeleted() {
        return status.isDeleted();
    }

    // ==================== 业务属性管理 ====================

    /**
     * 设置商品信息
     */
    public void setInfo(String name, String logo, String images, String description) {
        this.name = name;
        this.logo = logo;
        this.images = images;
        this.description = description;
        this.updateTime = new Date();
    }

    /**
     * 设置分类
     */
    public void setCategory(Integer cateId) {
        this.cateId = cateId;
        this.updateTime = new Date();
    }

    /**
     * 设置卖点
     */
    public void setSalePoint(String salePoint) {
        this.salePoint = salePoint;
        this.updateTime = new Date();
    }

    /**
     * 设置排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
        this.updateTime = new Date();
    }

    /**
     * 设置重量
     */
    public void setWeight(BigDecimal weight) {
        if (weight != null && weight.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("重量不能为负数");
        }
        this.weight = weight;
        this.updateTime = new Date();
    }

    /**
     * 设置关联卡券
     */
    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
        this.updateTime = new Date();
    }

    /**
     * 设置服务时长
     */
    public void setServiceTime(Integer serviceTime) {
        if (serviceTime != null && serviceTime < 0) {
            throw new IllegalArgumentException("服务时长不能为负数");
        }
        this.serviceTime = serviceTime;
        this.updateTime = new Date();
    }

    /**
     * 设置积分使用规则
     */
    public void setCanUsePoint(String canUsePoint) {
        this.canUsePoint = canUsePoint;
        this.updateTime = new Date();
    }

    /**
     * 设置会员折扣规则
     */
    public void setIsMemberDiscount(String isMemberDiscount) {
        this.isMemberDiscount = isMemberDiscount;
        this.updateTime = new Date();
    }

    /**
     * 是否可使用积分
     */
    public boolean canUsePoint() {
        return "Y".equals(this.canUsePoint);
    }

    /**
     * 是否有会员折扣
     */
    public boolean hasMemberDiscount() {
        return "Y".equals(this.isMemberDiscount);
    }

    /**
     * 获取商品编码值
     */
    public String getGoodsNoValue() {
        return goodsNo != null ? goodsNo.getValue() : null;
    }

    /**
     * 获取状态码
     */
    public String getStatusCode() {
        return status.getCode();
    }
}
