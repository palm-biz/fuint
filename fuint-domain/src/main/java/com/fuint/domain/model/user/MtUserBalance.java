package com.fuint.domain.model.user;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员余额实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtUserBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long userId;

    private BigDecimal amount;

    private LocalDateTime createTime;

    private LocalDateTime expired;

    private Long merchantId;

    private Long storeId;

    private String operator;

    private Long orderId;

    private String remark;

    private String status;

    private LocalDateTime updateTime;

}
