package cloud.palmbiz.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 卡券分组数据DTO
 */
@Data
public class GroupDataListDto {

    @ApiModelProperty("键值")
    private String key;

    @ApiModelProperty("数据")
    private GroupDataDto data;

}
