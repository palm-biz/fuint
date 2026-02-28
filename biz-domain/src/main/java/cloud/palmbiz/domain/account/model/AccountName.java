package cloud.palmbiz.domain.account.model;

import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 账号名称值对象
 * 封装账号名称的验证规则
 *
 * @author DDD Refactoring
 */
@Value
public class AccountName {

    String value;

    private AccountName(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("账号名称不能为空");
        }
        if (value.length() < 3 || value.length() > 50) {
            throw new IllegalArgumentException("账号名称长度必须在3-50个字符之间");
        }
        // 转换为小写存储
        this.value = value.toLowerCase();
    }

    /**
     * 创建账号名称
     */
    public static AccountName of(String value) {
        return new AccountName(value);
    }

    /**
     * 转换为字符串
     */
    public String toString() {
        return value;
    }
}
