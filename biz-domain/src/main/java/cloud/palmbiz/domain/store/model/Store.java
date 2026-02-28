package cloud.palmbiz.domain.store.model;

import lombok.Getter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺聚合根
 */
@Getter
public class Store {
    private Integer id;
    private Integer merchantId;
    private String name;
    private String logo;
    private String qrCode;
    private String isDefault;
    private String contact;
    private String phone;
    private String address;
    private Location location;
    private BigDecimal distance;
    private String hours;
    private String license;
    private String creditCode;
    private BankAccount bankAccount;
    private PaymentConfig paymentConfig;
    private String description;
    private Date createTime;
    private Date updateTime;
    private StoreStatus status;
    private String operator;

    /**
     * 创建新店铺
     */
    public static Store create(Integer merchantId, String name, String contact, String phone,
                               String address, String operator) {
        if (merchantId == null || merchantId <= 0) {
            throw new IllegalArgumentException("商户ID不能为空");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("店铺名称不能为空");
        }

        Store store = new Store();
        store.merchantId = merchantId;
        store.name = name;
        store.contact = contact;
        store.phone = phone;
        store.address = address;
        store.operator = operator;
        store.isDefault = "N";
        store.status = StoreStatus.ENABLED;
        store.bankAccount = BankAccount.empty();
        store.paymentConfig = PaymentConfig.empty();
        Date now = new Date();
        store.createTime = now;
        store.updateTime = now;
        return store;
    }

    /**
     * 重建店铺（从持久化数据恢复）
     */
    public static Store reconstitute(Integer id, Integer merchantId, String name, String logo,
                                     String qrCode, String isDefault, String contact, String phone,
                                     String address, String latitude, String longitude,
                                     BigDecimal distance, String hours, String license,
                                     String creditCode, String bankName, String bankCardName,
                                     String bankCardNo, String wxMchId, String wxApiV2,
                                     String wxCertPath, String alipayAppId, String alipayPrivateKey,
                                     String alipayPublicKey, String description, Date createTime,
                                     Date updateTime, String status, String operator) {
        Store store = new Store();
        store.id = id;
        store.merchantId = merchantId;
        store.name = name;
        store.logo = logo;
        store.qrCode = qrCode;
        store.isDefault = isDefault;
        store.contact = contact;
        store.phone = phone;
        store.address = address;
        store.location = (latitude != null && longitude != null)
            ? Location.of(latitude, longitude) : null;
        store.distance = distance;
        store.hours = hours;
        store.license = license;
        store.creditCode = creditCode;
        store.bankAccount = BankAccount.of(bankName, bankCardName, bankCardNo);
        store.paymentConfig = new PaymentConfig(wxMchId, wxApiV2, wxCertPath,
                alipayAppId, alipayPrivateKey, alipayPublicKey);
        store.description = description;
        store.createTime = createTime;
        store.updateTime = updateTime;
        store.status = StoreStatus.fromCode(status);
        store.operator = operator;
        return store;
    }

    /**
     * 更新基本信息
     */
    public void updateBasicInfo(String name, String logo, String contact, String phone,
                               String address, String hours, String description, String operator) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        this.logo = logo;
        this.contact = contact;
        this.phone = phone;
        this.address = address;
        this.hours = hours;
        this.description = description;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 设置位置信息
     */
    public void setLocation(String latitude, String longitude) {
        this.location = Location.of(latitude, longitude);
        this.updateTime = new Date();
    }

    /**
     * 设置为默认店铺
     */
    public void setAsDefault() {
        this.isDefault = "Y";
        this.updateTime = new Date();
    }

    /**
     * 取消默认店铺
     */
    public void unsetDefault() {
        this.isDefault = "N";
        this.updateTime = new Date();
    }

    /**
     * 是否为默认店铺
     */
    public boolean isDefault() {
        return "Y".equals(this.isDefault);
    }

    /**
     * 更新营业执照信息
     */
    public void updateLicenseInfo(String license, String creditCode) {
        this.license = license;
        this.creditCode = creditCode;
        this.updateTime = new Date();
    }

    /**
     * 更新银行账户信息
     */
    public void updateBankAccount(String bankName, String bankCardName, String bankCardNo) {
        this.bankAccount = BankAccount.of(bankName, bankCardName, bankCardNo);
        this.updateTime = new Date();
    }

    /**
     * 更新微信支付配置
     */
    public void updateWechatPayment(String wxMchId, String wxApiV2, String wxCertPath) {
        this.paymentConfig = this.paymentConfig.updateWechat(wxMchId, wxApiV2, wxCertPath);
        this.updateTime = new Date();
    }

    /**
     * 更新支付宝配置
     */
    public void updateAlipayPayment(String alipayAppId, String alipayPrivateKey, String alipayPublicKey) {
        this.paymentConfig = this.paymentConfig.updateAlipay(alipayAppId, alipayPrivateKey, alipayPublicKey);
        this.updateTime = new Date();
    }

    /**
     * 设置二维码
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
        this.updateTime = new Date();
    }

    /**
     * 设置距离
     */
    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    /**
     * 启用店铺
     */
    public void enable(String operator) {
        this.status = StoreStatus.ENABLED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 禁用店铺
     */
    public void disable(String operator) {
        this.status = StoreStatus.DISABLED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 删除店铺（软删除）
     */
    public void delete(String operator) {
        this.status = StoreStatus.DELETED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 是否激活
     */
    public boolean isActive() {
        return status == StoreStatus.ENABLED;
    }

    /**
     * 获取经度
     */
    public String getLatitude() {
        return location != null ? location.getLatitude() : null;
    }

    /**
     * 获取纬度
     */
    public String getLongitude() {
        return location != null ? location.getLongitude() : null;
    }
}
