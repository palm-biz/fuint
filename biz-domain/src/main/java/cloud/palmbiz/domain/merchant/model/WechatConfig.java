package cloud.palmbiz.domain.merchant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信配置值对象
 */
@Getter
@AllArgsConstructor
public class WechatConfig {
    // 小程序配置
    private String wxAppId;
    private String wxAppSecret;

    // 公众号配置
    private String wxOfficialAppId;
    private String wxOfficialAppSecret;

    public static WechatConfig empty() {
        return new WechatConfig(null, null, null, null);
    }

    public static WechatConfig of(String wxAppId, String wxAppSecret,
                                  String wxOfficialAppId, String wxOfficialAppSecret) {
        return new WechatConfig(wxAppId, wxAppSecret, wxOfficialAppId, wxOfficialAppSecret);
    }

    public boolean hasMiniProgramConfig() {
        return wxAppId != null && !wxAppId.isEmpty()
                && wxAppSecret != null && !wxAppSecret.isEmpty();
    }

    public boolean hasOfficialAccountConfig() {
        return wxOfficialAppId != null && !wxOfficialAppId.isEmpty()
                && wxOfficialAppSecret != null && !wxOfficialAppSecret.isEmpty();
    }

    public WechatConfig updateMiniProgram(String appId, String appSecret) {
        return new WechatConfig(appId, appSecret, this.wxOfficialAppId, this.wxOfficialAppSecret);
    }

    public WechatConfig updateOfficialAccount(String appId, String appSecret) {
        return new WechatConfig(this.wxAppId, this.wxAppSecret, appId, appSecret);
    }
}
