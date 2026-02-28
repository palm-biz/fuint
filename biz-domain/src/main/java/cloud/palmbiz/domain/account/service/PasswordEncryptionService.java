package cloud.palmbiz.domain.account.service;

import cloud.palmbiz.common.utils.Digests;
import cloud.palmbiz.common.utils.Encodes;
import org.springframework.stereotype.Service;

/**
 * 密码加密领域服务
 * 提供密码加密相关的核心算法
 *
 * @author DDD Refactoring
 */
@Service
public class PasswordEncryptionService {

    /**
     * 加密密码
     *
     * @param plainPassword 明文密码
     * @param salt 盐值（Hex编码）
     * @return 加密后的密码（Hex编码）
     */
    public String encrypt(String plainPassword, String salt) {
        byte[] saltBytes = Encodes.decodeHex(salt);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), saltBytes, 1024);
        return Encodes.encodeHex(hashPassword);
    }

    /**
     * 生成盐值
     *
     * @return Hex编码的盐值
     */
    public String generateSalt() {
        byte[] salt = Digests.generateSalt(8);
        return Encodes.encodeHex(salt);
    }

    /**
     * 验证密码是否匹配
     *
     * @param plainPassword 明文密码
     * @param encryptedPassword 加密后的密码
     * @param salt 盐值
     * @return 是否匹配
     */
    public boolean matches(String plainPassword, String encryptedPassword, String salt) {
        String encrypted = encrypt(plainPassword, salt);
        return encrypted.equals(encryptedPassword);
    }
}
