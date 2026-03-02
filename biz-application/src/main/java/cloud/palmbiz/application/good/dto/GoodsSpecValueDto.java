package cloud.palmbiz.application.good.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品规格值实体
 */
@Data
public class GoodsSpecValueDto implements Serializable {

    @ApiModelProperty("值ID")
    private Integer specValueId;

    @ApiModelProperty("规格名")
    private String specName;

    @ApiModelProperty("规格值")
    private String specValue;

}

