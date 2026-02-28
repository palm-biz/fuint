package cloud.palmbiz.domain.merchant.model;

import lombok.Getter;
import java.util.Date;

/**
 * 商户聚合根
 */
@Getter
public class Merchant {
    private Integer id;
    private MerchantNo merchantNo;
    private String name;
    private String type;
    private String logo;
    private String contact;
    private String phone;
    private String address;
    private WechatConfig wechatConfig;
    private SettleRate settleRate;
    private String description;
    private Date createTime;
    private Date updateTime;
    private MerchantStatus status;
    private String operator;

    /**
     * 创建新商户
     */
    public static Merchant create(String merchantNo, String name, String type,
                                 String contact, String phone, String operator) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("商户名称不能为空");
        }

        Merchant merchant = new Merchant();
        merchant.merchantNo = MerchantNo.of(merchantNo);
        merchant.name = name.trim();
        merchant.type = type;
        merchant.contact = contact;
        merchant.phone = phone;
        merchant.operator = operator;
        merchant.status = MerchantStatus.ENABLED;
        merchant.wechatConfig = WechatConfig.empty();
        merchant.settleRate = SettleRate.zero();
        Date now = new Date();
        merchant.createTime = now;
        merchant.updateTime = now;
        return merchant;
    }

    /**
     * 重建商户（从持久化数据恢复）
     */
    public static Merchant reconstitute(Integer id, String merchantNo, String name, String type,
                                       String logo, String contact, String phone, String address,
                                       String wxAppId, String wxAppSecret,
                                       String wxOfficialAppId, String wxOfficialAppSecret,
                                       java.math.BigDecimal settleRate, String description,
                                       Date createTime, Date updateTime,
                                       String status, String operator) {
        Merchant merchant = new Merchant();
        merchant.id = id;
        merchant.merchantNo = MerchantNo.of(merchantNo);
        merchant.name = name;
        merchant.type = type;
        merchant.logo = logo;
        merchant.contact = contact;
        merchant.phone = phone;
        merchant.address = address;
        merchant.wechatConfig = WechatConfig.of(wxAppId, wxAppSecret, wxOfficialAppId, wxOfficialAppSecret);
        merchant.settleRate = SettleRate.of(settleRate);
        merchant.description = description;
        merchant.createTime = createTime;
        merchant.updateTime = updateTime;
        merchant.status = MerchantStatus.fromCode(status);
        merchant.operator = operator;
        return merchant;
    }

    /**
     * 更新基本信息
     */
    public void updateBasicInfo(String name, String logo, String contact, String phone,
                               String address, String description, String operator) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
        this.logo = logo;
        this.contact = contact;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 更新类型
     */
    public void updateType(String type) {
        this.type = type;
        this.updateTime = new Date();
    }

    /**
     * 更新商户号
     */
    public void updateMerchantNo(String merchantNo) {
        this.merchantNo = MerchantNo.of(merchantNo);
        this.updateTime = new Date();
    }

    /**
     * 更新微信小程序配置
     */
    public void updateWechatMiniProgram(String appId, String appSecret) {
        this.wechatConfig = this.wechatConfig.updateMiniProgram(appId, appSecret);
        this.updateTime = new Date();
    }

    /**
     * 更新微信公众号配置
     */
    public void updateWechatOfficialAccount(String appId, String appSecret) {
        this.wechatConfig = this.wechatConfig.updateOfficialAccount(appId, appSecret);
        this.updateTime = new Date();
    }

    /**
     * 更新结算比例
     */
    public void updateSettleRate(java.math.BigDecimal rate) {
        this.settleRate = SettleRate.of(rate);
        this.updateTime = new Date();
    }

    /**
     * 启用商户
     */
    public void enable(String operator) {
        this.status = MerchantStatus.ENABLED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 禁用商户
     */
    public void disable(String operator) {
        this.status = MerchantStatus.DISABLED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 删除商户（软删除）
     */
    public void delete(String operator) {
        this.status = MerchantStatus.DELETED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 是否激活
     */
    public boolean isActive() {
        return status == MerchantStatus.ENABLED;
    }

    /**
     * 获取商户号字符串
     */
    public String getNo() {
        return merchantNo != null ? merchantNo.getValue() : null;
    }

    /**
     * 获取微信小程序AppId
     */
    public String getWxAppId() {
        return wechatConfig != null ? wechatConfig.getWxAppId() : null;
    }

    /**
     * 获取微信小程序秘钥
     */
    public String getWxAppSecret() {
        return wechatConfig != null ? wechatConfig.getWxAppSecret() : null;
    }

    /**
     * 获取微信公众号AppId
     */
    public String getWxOfficialAppId() {
        return wechatConfig != null ? wechatConfig.getWxOfficialAppId() : null;
    }

    /**
     * 获取微信公众号秘钥
     */
    public String getWxOfficialAppSecret() {
        return wechatConfig != null ? wechatConfig.getWxOfficialAppSecret() : null;
    }

    /**
     * 获取结算比例
     */
    public java.math.BigDecimal getSettleRateValue() {
        return settleRate != null ? settleRate.getValue() : null;
    }
}
