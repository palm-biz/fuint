package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员开卡赠礼
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtOpenGift implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long gradeId;

    private Long point;

    private Long couponId;

    private Long couponNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String operator;
}
