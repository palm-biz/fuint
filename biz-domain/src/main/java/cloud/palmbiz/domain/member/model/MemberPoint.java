package cloud.palmbiz.domain.member.model;

import lombok.Value;

/**
 * 会员积分值对象
 *
 * @author DDD Refactoring
 */
@Value
public class MemberPoint {

    Integer value;

    private MemberPoint(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("积分不能为null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("积分不能为负数");
        }
        this.value = value;
    }

    public static MemberPoint of(Integer value) {
        return new MemberPoint(value);
    }

    public static MemberPoint zero() {
        return new MemberPoint(0);
    }

    /**
     * 增加积分
     */
    public MemberPoint add(Integer points) {
        if (points <= 0) {
            throw new IllegalArgumentException("增加积分必须大于0");
        }
        return new MemberPoint(this.value + points);
    }

    /**
     * 扣减积分
     */
    public MemberPoint deduct(Integer points) {
        if (points <= 0) {
            throw new IllegalArgumentException("扣减积分必须大于0");
        }
        if (this.value < points) {
            throw new IllegalArgumentException("积分不足");
        }
        return new MemberPoint(this.value - points);
    }

    /**
     * 是否足够
     */
    public boolean isEnough(Integer points) {
        return this.value >= points;
    }

    public Integer toInteger() {
        return value;
    }
}
