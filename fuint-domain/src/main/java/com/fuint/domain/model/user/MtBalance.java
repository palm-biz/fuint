package com.fuint.domain.model.user;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 余额变化表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String mobile;

    private Long userId;

    private String orderSn;

    private BigDecimal amount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String status;

}
