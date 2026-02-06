package com.fuint.domain.model.order;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 订单收货地址记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtOrderAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String name;

    private String mobile;

    private Long provinceId;

    private Long cityId;

    private Long regionId;

    private String detail;

    private Long orderId;

    private Long userId;

    private LocalDateTime createTime;


}
