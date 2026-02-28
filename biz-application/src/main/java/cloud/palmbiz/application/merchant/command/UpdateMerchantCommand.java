package cloud.palmbiz.application.merchant.command;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 更新商户命令
 */
@Data
public class UpdateMerchantCommand {
    private Integer id;
    private String merchantNo;
    private String name;
    private String type;
    private String logo;
    private String contact;
    private String phone;
    private String address;
    private String wxAppId;
    private String wxAppSecret;
    private String wxOfficialAppId;
    private String wxOfficialAppSecret;
    private BigDecimal settleRate;
    private String description;
    private String status;
    private String operator;
}
