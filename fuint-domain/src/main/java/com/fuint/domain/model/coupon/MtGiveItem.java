package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 转赠明细表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtGiveItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long giveId;

    private Long userCouponId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

}
