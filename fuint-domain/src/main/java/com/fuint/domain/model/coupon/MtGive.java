package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 转赠记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtGive implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private Long giveUserId;

    private String mobile;

    private String userMobile;

    private String groupIds;

    private String groupNames;

    private String couponIds;

    private String couponNames;

    private Long num;

    private BigDecimal money;

    private String note;

    private String message;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;


}
