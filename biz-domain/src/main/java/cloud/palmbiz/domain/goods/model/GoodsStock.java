package cloud.palmbiz.domain.goods.model;

import lombok.Value;

/**
 * 商品库存值对象
 *
 * @author DDD Refactoring
 */
@Value
public class GoodsStock {

    Double value;

    private GoodsStock(Double value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("库存不能为负数");
        }
        this.value = value;
    }

    public static GoodsStock of(Double value) {
        return new GoodsStock(value);
    }

    public static GoodsStock zero() {
        return new GoodsStock(0.0);
    }

    /**
     * 增加库存
     */
    public GoodsStock add(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("增加库存数量必须大于0");
        }
        return new GoodsStock(this.value + amount);
    }

    /**
     * 扣减库存
     */
    public GoodsStock subtract(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("扣减库存数量必须大于0");
        }
        if (this.value < amount) {
            throw new IllegalArgumentException("库存不足");
        }
        return new GoodsStock(this.value - amount);
    }

    /**
     * 是否有库存
     */
    public boolean hasStock() {
        return value != null && value > 0;
    }

    /**
     * 是否库存充足
     */
    public boolean isSufficient(Double required) {
        if (required == null || required <= 0) {
            return true;
        }
        return value >= required;
    }

    public Double toDouble() {
        return value;
    }
}
