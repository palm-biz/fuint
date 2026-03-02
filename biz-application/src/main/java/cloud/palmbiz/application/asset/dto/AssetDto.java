package cloud.palmbiz.appointment.asset.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 个人资产实体类
 */
@Data
public class AssetDto {

    @ApiModelProperty("次卡数量")
    private Integer timer;

    @ApiModelProperty("储值卡数量")
    private Integer prestore;

    @ApiModelProperty("优惠券数量")
    private Integer coupon;
}
