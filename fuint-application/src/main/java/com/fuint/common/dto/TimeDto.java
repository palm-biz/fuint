package com.fuint.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 时间Dto
 */
@Data
public class TimeDto implements Serializable {

    @ApiModelProperty("时间段")
    private String time;

    @ApiModelProperty("是否可预订")
    private Boolean enable;
}
