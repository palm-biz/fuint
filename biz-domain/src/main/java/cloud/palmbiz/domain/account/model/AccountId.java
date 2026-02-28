package cloud.palmbiz.domain.account.model;

import lombok.Value;

/**
 * 账号ID值对象
 * 提供类型安全和业务含义
 *
 * @author DDD Refactoring
 */
@Value
public class AccountId {

    Integer value;

    private AccountId(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("账号ID必须是正整数");
        }
        this.value = value;
    }

    /**
     * 创建账号ID
     */
    public static AccountId of(Integer value) {
        return new AccountId(value);
    }

    /**
     * 转换为Integer类型
     */
    public Integer toInteger() {
        return value;
    }
}
