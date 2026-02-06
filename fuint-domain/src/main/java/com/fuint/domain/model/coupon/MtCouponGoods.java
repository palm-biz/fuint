package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 卡券商品表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCouponGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long couponId;

    private Long goodsId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

}
