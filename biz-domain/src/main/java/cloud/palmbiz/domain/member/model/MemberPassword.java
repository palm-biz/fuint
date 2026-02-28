package cloud.palmbiz.domain.member.model;

import cloud.palmbiz.common.util.MD5Util;
import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 会员密码值对象
 * 使用 MD5 + Salt 加密
 *
 * @author DDD Refactoring
 */
@Value
public class MemberPassword {

    String encryptedValue;
    String salt;

    private MemberPassword(String encryptedValue, String salt) {
        if (StringUtils.isEmpty(encryptedValue)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (StringUtils.isEmpty(salt)) {
            throw new IllegalArgumentException("盐值不能为空");
        }
        this.encryptedValue = encryptedValue;
        this.salt = salt;
    }

    /**
     * 从明文创建密码（新建会员时使用）
     */
    public static MemberPassword fromPlainText(String plainPassword, String salt) {
        if (StringUtils.isEmpty(plainPassword)) {
            throw new IllegalArgumentException("明文密码不能为空");
        }
        if (StringUtils.isEmpty(salt)) {
            throw new IllegalArgumentException("盐值不能为空");
        }
        String encrypted = MD5Util.getMD5(plainPassword + salt);
        return new MemberPassword(encrypted, salt);
    }

    /**
     * 从已加密的密码创建（从数据库加载时使用）
     */
    public static MemberPassword fromEncrypted(String encryptedPassword, String salt) {
        return new MemberPassword(encryptedPassword, salt);
    }

    /**
     * 验证明文密码是否匹配
     */
    public boolean matches(String plainPassword) {
        if (StringUtils.isEmpty(plainPassword)) {
            return false;
        }
        String encrypted = MD5Util.getMD5(plainPassword + salt);
        return this.encryptedValue.equals(encrypted);
    }
}
