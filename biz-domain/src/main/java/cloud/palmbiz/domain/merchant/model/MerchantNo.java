package cloud.palmbiz.domain.merchant.model;

import lombok.Value;

/**
 * 商户号值对象
 */
@Value
public class MerchantNo {
    String value;

    public static MerchantNo of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("商户号不能为空");
        }

        // 商户号不能含有中文
        if (containsChinese(value)) {
            throw new IllegalArgumentException("商户号不能含有中文字符");
        }

        // 商户号长度限制
        if (value.length() > 50) {
            throw new IllegalArgumentException("商户号长度不能超过50个字符");
        }

        return new MerchantNo(value.trim());
    }

    private static boolean containsChinese(String str) {
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
