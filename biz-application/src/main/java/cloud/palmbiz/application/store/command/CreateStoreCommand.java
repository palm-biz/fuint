package cloud.palmbiz.application.store.command;

import lombok.Data;

/**
 * 创建店铺命令
 */
@Data
public class CreateStoreCommand {
    private Integer merchantId;
    private String name;
    private String logo;
    private String contact;
    private String phone;
    private String address;
    private String latitude;
    private String longitude;
    private String hours;
    private String license;
    private String creditCode;
    private String bankName;
    private String bankCardName;
    private String bankCardNo;
    private String wxMchId;
    private String wxApiV2;
    private String wxCertPath;
    private String alipayAppId;
    private String alipayPrivateKey;
    private String alipayPublicKey;
    private String description;
    private String isDefault;
    private String operator;
}
