package cloud.palmbiz.application.order.dto;

import cloud.palmbiz.application.marketing.coupon.dto.CouponDto;
import cloud.palmbiz.common.store.dto.StoreInfo;
import cloud.palmbiz.common.user.dto.UserDto;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 核销卡券流水 dto
 */
@Data
public class WriteOffDto implements Serializable {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("核销编码")
    private String code;

    @ApiModelProperty("核销状态")
    private String status;

    @ApiModelProperty("会员卡券ID")
    private Integer userCouponId;

    @ApiModelProperty("卡券信息")
    private CouponDto couponInfo;

    @ApiModelProperty("会员信息")
    private UserDto userInfo;

    @ApiModelProperty("核销店铺信息")
    private StoreInfo storeInfo;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("核销金额")
    private BigDecimal amount;

    @ApiModelProperty("核销uuid")
    private String uuid;

    @ApiModelProperty("核销备注")
    private String remark;

    @ApiModelProperty("最后操作人")
    private String operator;


}
