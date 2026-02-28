package cloud.palmbiz.domain.merchant.service;

import cloud.palmbiz.framework.exception.BusinessCheckException;
import org.springframework.stereotype.Service;

/**
 * 商户验证领域服务
 */
@Service
public class MerchantValidationService {

    /**
     * 验证商户名称
     */
    public void validateMerchantName(String name) throws BusinessCheckException {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessCheckException("商户名称不能为空");
        }
        if (name.length() > 100) {
            throw new BusinessCheckException("商户名称长度不能超过100个字符");
        }
    }

    /**
     * 验证商户号
     */
    public void validateMerchantNo(String merchantNo) throws BusinessCheckException {
        if (merchantNo == null || merchantNo.trim().isEmpty()) {
            throw new BusinessCheckException("商户号不能为空");
        }

        // 商户号不能含有中文
        if (containsChinese(merchantNo)) {
            throw new BusinessCheckException("商户号不能含有中文字符");
        }

        if (merchantNo.length() > 50) {
            throw new BusinessCheckException("商户号长度不能超过50个字符");
        }
    }

    /**
     * 验证联系电话
     */
    public void validatePhone(String phone) throws BusinessCheckException {
        if (phone == null || phone.trim().isEmpty()) {
            return;
        }
        // 简单的手机号验证
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new BusinessCheckException("联系电话格式不正确");
        }
    }

    /**
     * 验证结算比例
     */
    public void validateSettleRate(java.math.BigDecimal rate) throws BusinessCheckException {
        if (rate == null) {
            return;
        }
        if (rate.compareTo(java.math.BigDecimal.ZERO) < 0
                || rate.compareTo(new java.math.BigDecimal("100")) > 0) {
            throw new BusinessCheckException("结算比例必须在0-100之间");
        }
    }

    /**
     * 检查字符串是否包含中文
     */
    private boolean containsChinese(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x4E00 && c <= 0x9FA5) {
                return true;
            }
        }
        return false;
    }
}
