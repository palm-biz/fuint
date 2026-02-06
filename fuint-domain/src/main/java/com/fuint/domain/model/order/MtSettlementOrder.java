package com.fuint.domain.model.order;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 结算订单表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtSettlementOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long settlementId;

    private Long orderId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String status;

}
