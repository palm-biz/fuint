package cloud.palmbiz.domain.goods.model;

import lombok.Value;

import java.math.BigDecimal;

/**
 * 商品价格值对象
 * 封装商品价格相关的计算逻辑
 *
 * @author DDD Refactoring
 */
@Value
public class GoodsPrice {

    /**
     * 销售价格
     */
    BigDecimal price;

    /**
     * 划线价格
     */
    BigDecimal linePrice;

    /**
     * 成本价格
     */
    BigDecimal costPrice;

    private GoodsPrice(BigDecimal price, BigDecimal linePrice, BigDecimal costPrice) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("销售价格不能为负数");
        }
        if (linePrice != null && linePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("划线价格不能为负数");
        }
        if (costPrice != null && costPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("成本价格不能为负数");
        }
        this.price = price;
        this.linePrice = linePrice != null ? linePrice : BigDecimal.ZERO;
        this.costPrice = costPrice != null ? costPrice : BigDecimal.ZERO;
    }

    public static GoodsPrice of(BigDecimal price, BigDecimal linePrice, BigDecimal costPrice) {
        return new GoodsPrice(price, linePrice, costPrice);
    }

    public static GoodsPrice simple(BigDecimal price) {
        return new GoodsPrice(price, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    /**
     * 是否有划线价
     */
    public boolean hasLinePrice() {
        return linePrice != null && linePrice.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 计算利润
     */
    public BigDecimal calculateProfit() {
        if (costPrice == null || costPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return price;
        }
        return price.subtract(costPrice);
    }

    /**
     * 计算利润率
     */
    public BigDecimal calculateProfitRate() {
        if (costPrice == null || costPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ONE;
        }
        BigDecimal profit = calculateProfit();
        return profit.divide(costPrice, 4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 应用折扣
     */
    public GoodsPrice applyDiscount(BigDecimal discountRate) {
        if (discountRate == null || discountRate.compareTo(BigDecimal.ZERO) <= 0
                || discountRate.compareTo(BigDecimal.ONE) >= 0) {
            return this;
        }
        BigDecimal newPrice = price.multiply(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        return new GoodsPrice(newPrice, linePrice, costPrice);
    }
}
