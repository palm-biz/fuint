package cloud.palmbiz.domain.member.service;

import cloud.palmbiz.common.util.MD5Util;
import cloud.palmbiz.common.util.SeqUtil;
import org.springframework.stereotype.Service;

/**
 * 会员密码领域服务
 * 提供密码加密相关的核心算法
 *
 * @author DDD Refactoring
 */
@Service
public class MemberPasswordService {

    /**
     * 生成盐值
     *
     * @return 4位随机字母
     */
    public String generateSalt() {
        return SeqUtil.getRandomLetter(4);
    }

    /**
     * 加密密码
     *
     * @param plainPassword 明文密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public String encrypt(String plainPassword, String salt) {
        return MD5Util.getMD5(plainPassword + salt);
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
