package cloud.palmbiz.application.temporal.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 日期Dto
 */
@Data
public class DayDto implements Serializable {

    @ApiModelProperty("星期")
    private String week;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("是否可预订")
    private Boolean enable;
}
