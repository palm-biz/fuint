package cloud.palmbiz.application.coupon.query;

import lombok.Data;

@Data
public class CouponPageQuery {
    private Integer merchantId;
    private Integer storeId;
    private String type;
    private String status;
    private String keyword;
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
}
