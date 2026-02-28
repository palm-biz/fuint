package cloud.palmbiz.application.order.command;

import lombok.Data;

/**
 * 取消订单命令
 *
 * @author DDD Refactoring
 */
@Data
public class CancelOrderCommand {

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 是否需要退款
     */
    private Boolean needRefund;
}
