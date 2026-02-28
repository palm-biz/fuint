package cloud.palmbiz.domain.member.model;

import lombok.Value;

/**
 * 会员ID值对象
 *
 * @author DDD Refactoring
 */
@Value
public class MemberId {

    Integer value;

    private MemberId(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("会员ID必须是正整数");
        }
        this.value = value;
    }

    public static MemberId of(Integer value) {
        return new MemberId(value);
    }

    public Integer toInteger() {
        return value;
    }
}
