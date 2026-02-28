package cloud.palmbiz.domain.cart.model;

import lombok.Value;

/**
 * 购物车商品数量值对象
 *
 * @author DDD Refactoring
 */
@Value
public class CartQuantity {

    Double value;

    private CartQuantity(Double value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("商品数量必须大于0");
        }
        this.value = value;
    }

    public static CartQuantity of(Double value) {
        return new CartQuantity(value);
    }

    /**
     * 增加数量
     */
    public CartQuantity add(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("增加数量必须大于0");
        }
        return new CartQuantity(this.value + amount);
    }

    /**
     * 减少数量
     */
    public CartQuantity subtract(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("减少数量必须大于0");
        }
        double newValue = this.value - amount;
        if (newValue <= 0) {
            throw new IllegalArgumentException("数量不足，无法减少");
        }
        return new CartQuantity(newValue);
    }

    /**
     * 设置数量
     */
    public CartQuantity set(Double amount) {
        return new CartQuantity(amount);
    }

    public Double toDouble() {
        return value;
    }
}
