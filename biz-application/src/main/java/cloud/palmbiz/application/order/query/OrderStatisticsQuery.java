package cloud.palmbiz.application.order.query;

import lombok.Data;

/**
 * 订单统计查询
 *
 * @author DDD Refactoring
 */
@Data
public class OrderStatisticsQuery {

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
     * 统计类型（day/week/month/year/custom）
     */
    private String statisticsType;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
