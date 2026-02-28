package cloud.palmbiz.domain.cart.model;

import lombok.Value;

/**
 * 购物车ID值对象
 *
 * @author DDD Refactoring
 */
@Value
public class CartId {

    Integer value;

    private CartId(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("购物车ID必须是正整数");
        }
        this.value = value;
    }

    public static CartId of(Integer value) {
        return new CartId(value);
    }

    public Integer toInteger() {
        return value;
    }
}
