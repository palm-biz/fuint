package cloud.palmbiz.domain.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付配置值对象
 */
@Getter
@AllArgsConstructor
public class PaymentConfig {
    // 微信支付配置
    private String wxMchId;
    private String wxApiV2;
    private String wxCertPath;

    // 支付宝配置
    private String alipayAppId;
    private String alipayPrivateKey;
    private String alipayPublicKey;

    public static PaymentConfig empty() {
        return new PaymentConfig(null, null, null, null, null, null);
    }

    public boolean hasWechatConfig() {
        return wxMchId != null && !wxMchId.isEmpty();
    }

    public boolean hasAlipayConfig() {
        return alipayAppId != null && !alipayAppId.isEmpty();
    }

    public PaymentConfig updateWechat(String mchId, String apiV2, String certPath) {
        return new PaymentConfig(mchId, apiV2, certPath,
                this.alipayAppId, this.alipayPrivateKey, this.alipayPublicKey);
    }

    public PaymentConfig updateAlipay(String appId, String privateKey, String publicKey) {
        return new PaymentConfig(this.wxMchId, this.wxApiV2, this.wxCertPath,
                appId, privateKey, publicKey);
    }
}
