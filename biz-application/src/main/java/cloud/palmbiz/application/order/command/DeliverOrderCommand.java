package cloud.palmbiz.application.order.command;

import lombok.Data;

/**
 * 发货命令
 *
 * @author DDD Refactoring
 */
@Data
public class DeliverOrderCommand {

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 物流信息（JSON）
     */
    private String expressInfo;

    /**
     * 物流公司
     */
    private String expressCompany;

    /**
     * 物流单号
     */
    private String expressNo;

    /**
     * 操作人
     */
    private String operator;
}
