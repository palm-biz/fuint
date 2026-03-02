package cloud.palmbiz.application.card.dto;

import cloud.palmbiz.common.coupon.dto.CouponDto;
import cloud.palmbiz.common.store.dto.StoreInfo;
import cloud.palmbiz.infrastructure.model.MtCoupon;
import cloud.palmbiz.infrastructure.model.MtStore;
import cloud.palmbiz.infrastructure.model.MtUserGrade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开卡赠礼实体类
 */
@Data
public class OpenGiftDto implements Serializable {

    @ApiModelProperty("自增ID")
    private Integer id;

    @ApiModelProperty("店铺信息")
    private StoreInfo storeInfo;

    @ApiModelProperty("会员等级信息")
    private MtUserGrade gradeInfo;

    @ApiModelProperty("赠送积分")
    private Integer point;

    @ApiModelProperty("卡券信息")
    private CouponDto couponInfo;

    @ApiModelProperty("卡券数量")
    private Integer couponNum;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("最后操作人")
    private String operator;

}

