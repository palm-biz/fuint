package cloud.palmbiz.application.order.command;

import lombok.Data;

/**
 * 核销订单命令
 *
 * @author DDD Refactoring
 */
@Data
public class ConfirmOrderCommand {

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 核销验证码
     */
    private String verifyCode;

    /**
     * 核销备注
     */
    private String confirmRemark;

    /**
     * 核销员工ID
     */
    private Integer staffId;

    /**
     * 操作人
     */
    private String operator;
}
