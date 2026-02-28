package cloud.palmbiz.application.order.query;

import lombok.Data;

import java.util.Date;

/**
 * 订单分页查询对象
 *
 * @author DDD Refactoring
 */
@Data
public class OrderPageQuery {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单类型
     */
    private String type;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 核销状态
     */
    private String confirmStatus;

    /**
     * 结算状态
     */
    private String settleStatus;

    /**
     * 下单平台
     */
    private String platform;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 手机号（模糊查询）
     */
    private String mobile;

    /**
     * 用户名（模糊查询）
     */
    private String userName;

    /**
     * 页码
     */
    private Integer pageNumber = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方式（ASC/DESC）
     */
    private String sortOrder = "DESC";
}
