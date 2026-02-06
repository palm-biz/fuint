package com.fuint.domain.model.commission;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 佣金记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCommissionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String target;

    private String type;

    private Long level;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private Long staffId;

    private Long orderId;

    private BigDecimal amount;

    private Long ruleId;

    private Long ruleItemId;

    private String description;

    private String settleUuid;

    private Long cashId;

    private String isCash;

    private LocalDateTime cashTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
