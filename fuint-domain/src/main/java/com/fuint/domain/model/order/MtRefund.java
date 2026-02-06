package com.fuint.domain.model.order;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 售后表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long orderId;

    private Long merchantId;

    private Long storeId;

    private Long userId;

    private BigDecimal amount;

    private String type;

    private String remark;

    private String expressName;

    private String expressNo;

    private String rejectReason;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String images;

    private String operator;

}
