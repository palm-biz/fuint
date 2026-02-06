package com.fuint.domain.model.order;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 结算表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtSettlement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String settlementNo;

    private Long merchantId;

    private Long storeId;

    private BigDecimal settleRate;

    private BigDecimal totalOrderAmount;

    private BigDecimal amount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String payStatus;

    private String status;

}
