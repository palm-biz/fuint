package cloud.palmbiz.domain.merchant.model;

import lombok.Value;

/**
 * 商户ID值对象
 */
@Value(staticConstructor = "of")
public class MerchantId {
    Integer value;

    public static MerchantId of(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("商户ID必须大于0");
        }
        return new MerchantId(value);
    }
}
