package cloud.palmbiz.application.good.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品规格项实体
 */
@Data
public class GoodsSpecItemDto implements Serializable {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("规格名称")
    private String name;

    @ApiModelProperty("规格子类")
    private List<GoodsSpecChildDto> child;

}

