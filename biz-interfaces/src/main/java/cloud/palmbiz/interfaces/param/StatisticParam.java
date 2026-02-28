package cloud.palmbiz.interfaces.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 统计请求参数
 */
@Data
public class StatisticParam implements Serializable {

    @ApiModelProperty(value="开始时间", name="startTime")
    private String startTime;

    @ApiModelProperty(value="结束时间", name="endTime")
    private String endTime;

    @ApiModelProperty(value="店铺ID", name="storeId")
    private Integer storeId;

}
