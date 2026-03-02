package cloud.palmbiz.application.marketing.coupon.command;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateCouponCommand {
    private String name;
    private String type;
    private Integer merchantId;
    private Integer storeId;
    private BigDecimal amount;
    private Integer total;
    private Integer limitNum;
    private String expireType;
    private Integer expireTime;
    private Date beginTime;
    private Date endTime;
    private String description;
    private String image;
    private Boolean isGive;
    private String receiveCode;
}
