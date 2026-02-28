package cloud.palmbiz.domain.order.model;

import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 订单号值对象
 *
 * @author DDD Refactoring
 */
@Value
public class OrderNo {

    String value;

    private OrderNo(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("订单号不能为空");
        }
        this.value = value;
    }

    public static OrderNo of(String value) {
        return new OrderNo(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
