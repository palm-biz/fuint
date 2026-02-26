package cloud.palmbiz.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 积分转赠请求参数
 */
@Data
public class GivePointParam implements Serializable {

    @ApiModelProperty(value="手机号", name="mobile")
    private String mobile;

    @ApiModelProperty(value="转赠数量", name="amount")
    private Integer amount;

    @ApiModelProperty(value="转赠备注", name="remark")
    private String remark;

}
