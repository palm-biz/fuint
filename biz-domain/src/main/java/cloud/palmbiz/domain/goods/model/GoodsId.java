package cloud.palmbiz.domain.goods.model;

import lombok.Value;

/**
 * 商品ID值对象
 *
 * @author DDD Refactoring
 */
@Value
public class GoodsId {

    Integer value;

    private GoodsId(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("商品ID必须是正整数");
        }
        this.value = value;
    }

    public static GoodsId of(Integer value) {
        return new GoodsId(value);
    }

    public Integer toInteger() {
        return value;
    }
}
