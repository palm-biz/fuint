package cloud.palmbiz.appointment.region.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 地区实体类
 */
@Data
public class RegionDto implements Serializable {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("父ID")
    private Integer pid;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("层级")
    private String level;

    @ApiModelProperty("城市")
    private List<RegionDto> city;

    @ApiModelProperty("区域")
    private List<RegionDto> region;

}
