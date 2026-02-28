package cloud.palmbiz.application.order.command;

import lombok.Data;

/**
 * 支付订单命令
 *
 * @author DDD Refactoring
 */
@Data
public class PayOrderCommand {

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 支付流水号（第三方支付）
     */
    private String transactionId;

    /**
     * 支付渠道
     */
    private String payChannel;
}
