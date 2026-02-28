package cloud.palmbiz.domain.merchant.model;

import lombok.Value;

import java.math.BigDecimal;

/**
 * 结算比例值对象
 */
@Value
public class SettleRate {
    BigDecimal value;

    public static SettleRate of(BigDecimal value) {
        if (value == null) {
            return new SettleRate(BigDecimal.ZERO);
        }

        // 结算比例必须在0-100之间
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("结算比例必须在0-100之间");
        }

        return new SettleRate(value);
    }

    public static SettleRate zero() {
        return new SettleRate(BigDecimal.ZERO);
    }

    public BigDecimal asPercentage() {
        return value.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
    }
}
