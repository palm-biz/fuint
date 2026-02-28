package cloud.palmbiz.domain.order.model;

import lombok.Value;

/**
 * 订单ID值对象
 *
 * @author DDD Refactoring
 */
@Value
public class OrderId {

    Integer value;

    private OrderId(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("订单ID必须是正整数");
        }
        this.value = value;
    }

    public static OrderId of(Integer value) {
        return new OrderId(value);
    }

    public Integer toInteger() {
        return value;
    }
}
