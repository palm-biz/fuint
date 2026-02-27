package cloud.palmbiz.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商户设置实体
 */
@Data
public class MerchantSettingDto implements Serializable {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("商户名称")
    private String name;

    @ApiModelProperty("商户logo")
    private String logo;

    @ApiModelProperty("联系人姓名")
    private String contact;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("营业状态")
    private String status;

}
