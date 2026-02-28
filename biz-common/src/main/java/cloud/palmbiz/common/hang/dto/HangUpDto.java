package cloud.palmbiz.common.hang.dto;

import cloud.palmbiz.common.user.dto.UserInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 收银挂单实体类
 */
@Data
public class HangUpDto {

    @ApiModelProperty("挂单号")
    private String hangNo;

    @ApiModelProperty("是否空白")
    private Boolean isEmpty;

    @ApiModelProperty("会员信息")
    private UserInfoDto memberInfo;

    @ApiModelProperty("件数")
    private Double num;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("时间")
    private String dateTime;

}
