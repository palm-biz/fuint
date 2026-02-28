package cloud.palmbiz.domain.account.model;

import cloud.palmbiz.common.utils.Digests;
import cloud.palmbiz.common.utils.Encodes;
import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 密码值对象
 * 封装密码的加密、验证逻辑
 *
 * @author DDD Refactoring
 */
@Value
public class Password {

    /**
     * 加密后的密码
     */
    String encryptedValue;

    /**
     * 盐值
     */
    String salt;

    private Password(String encryptedValue, String salt) {
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
     * 从明文创建密码对象（新建账号时使用）
     */
    public static Password fromPlainText(String plainPassword) {
        if (StringUtils.isEmpty(plainPassword)) {
            throw new IllegalArgumentException("明文密码不能为空");
        }
        if (plainPassword.length() < 6) {
            throw new IllegalArgumentException("密码长度不能少于6位");
        }

        // 生成盐值
        byte[] saltBytes = Digests.generateSalt(8);
        String salt = Encodes.encodeHex(saltBytes);

        // 加密密码
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), saltBytes, 1024);
        String encryptedValue = Encodes.encodeHex(hashPassword);

        return new Password(encryptedValue, salt);
    }

    /**
     * 从已加密的密码和盐值创建密码对象（从数据库加载时使用）
     */
    public static Password fromEncrypted(String encryptedPassword, String salt) {
        return new Password(encryptedPassword, salt);
    }

    /**
     * 验证明文密码是否匹配
     */
    public boolean matches(String plainPassword) {
        if (StringUtils.isEmpty(plainPassword)) {
            return false;
        }

        byte[] saltBytes = Encodes.decodeHex(this.salt);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), saltBytes, 1024);
        String encryptedInput = Encodes.encodeHex(hashPassword);

        return this.encryptedValue.equals(encryptedInput);
    }

    /**
     * 修改密码（生成新的密码对象）
     */
    public Password change(String newPlainPassword) {
        return fromPlainText(newPlainPassword);
    }

    /**
     * 获取加密后的密码
     */
    public String getEncryptedValue() {
        return encryptedValue;
    }

    /**
     * 获取盐值
     */
    public String getSalt() {
        return salt;
    }
}
