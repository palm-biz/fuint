package cloud.palmbiz.domain.store.model;

import lombok.Value;

/**
 * 店铺ID值对象
 */
@Value(staticConstructor = "of")
public class StoreId {
    Integer value;

    public static StoreId of(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("店铺ID必须大于0");
        }
        return new StoreId(value);
    }
}
