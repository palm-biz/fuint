package cloud.palmbiz.application.marketing.coupon.command;

import lombok.Data;

@Data
public class UpdateCouponStatusCommand {
    private Integer couponId;
    private String status;
    private String operator;
}
