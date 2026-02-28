package cloud.palmbiz.domain.member.model;

import lombok.Value;

import java.math.BigDecimal;

/**
 * 会员余额值对象
 *
 * @author DDD Refactoring
 */
@Value
public class MemberBalance {

    BigDecimal value;

    private MemberBalance(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("余额不能为null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("余额不能为负数");
        }
        this.value = value;
    }

    public static MemberBalance of(BigDecimal value) {
        return new MemberBalance(value);
    }

    public static MemberBalance zero() {
        return new MemberBalance(BigDecimal.ZERO);
    }

    /**
     * 增加余额
     */
    public MemberBalance add(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("增加金额必须大于0");
        }
        return new MemberBalance(this.value.add(amount));
    }

    /**
     * 扣减余额
     */
    public MemberBalance deduct(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("扣减金额必须大于0");
        }
        if (this.value.compareTo(amount) < 0) {
            throw new IllegalArgumentException("余额不足");
        }
        return new MemberBalance(this.value.subtract(amount));
    }

    /**
     * 是否足够
     */
    public boolean isEnough(BigDecimal amount) {
        return this.value.compareTo(amount) >= 0;
    }

    public BigDecimal toBigDecimal() {
        return value;
    }
}
