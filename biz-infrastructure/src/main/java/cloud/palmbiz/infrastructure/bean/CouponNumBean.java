package cloud.palmbiz.infrastructure.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 卡券数量对象
 */
@Data
@ApiModel(value = "卡券数量对象", description = "卡券数量对象")
public class CouponNumBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("卡券ID")
    private Integer couponId;

    @ApiModelProperty("数量")
    private Long num;

}
