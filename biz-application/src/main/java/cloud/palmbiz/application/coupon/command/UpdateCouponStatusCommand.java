package cloud.palmbiz.application.coupon.command;

import lombok.Data;

@Data
public class UpdateCouponStatusCommand {
    private Integer couponId;
    private String status;
    private String operator;
}
