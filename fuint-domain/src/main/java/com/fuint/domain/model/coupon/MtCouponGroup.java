package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 优惠券组
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCouponGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String name;

    private BigDecimal money;

    private Long num;

    private Long total;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;


}
