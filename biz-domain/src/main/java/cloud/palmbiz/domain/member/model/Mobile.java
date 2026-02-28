package cloud.palmbiz.domain.member.model;

import cloud.palmbiz.common.util.PhoneFormatCheckUtils;
import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 手机号值对象
 *
 * @author DDD Refactoring
 */
@Value
public class Mobile {

    String value;

    private Mobile(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(value)) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
        this.value = value;
    }

    public static Mobile of(String value) {
        return new Mobile(value);
    }

    /**
     * 隐藏中间4位
     */
    public String hide() {
        return value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    @Override
    public String toString() {
        return value;
    }
}
