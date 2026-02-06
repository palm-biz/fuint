package com.fuint.domain.model.order;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 订单表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String type;

    private String payType;

    private String orderMode;

    private String platform;

    private String orderSn;

    private Long couponId;

    private Long merchantId;

    private Long storeId;

    private Long userId;

    private String verifyCode;

    private String isVisitor;

    private BigDecimal amount;

    private BigDecimal payAmount;

    private String settleStatus;

    private Long usePoint;

    private BigDecimal pointAmount;

    private BigDecimal discount;

    private BigDecimal deliveryFee;

    private String param;

    private String expressInfo;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private LocalDateTime payTime;

    private String payStatus;

    private Long staffId;

    private String confirmStatus;

    private LocalDateTime confirmTime;

    private String confirmRemark;

    private Long commissionUserId;

    private String commissionStatus;

    private String operator;

}
