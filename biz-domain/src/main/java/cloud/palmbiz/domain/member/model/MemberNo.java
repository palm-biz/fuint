package cloud.palmbiz.domain.member.model;

import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 会员号值对象
 *
 * @author DDD Refactoring
 */
@Value
public class MemberNo {

    String value;

    private MemberNo(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("会员号不能为空");
        }
        this.value = value;
    }

    public static MemberNo of(String value) {
        return new MemberNo(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
