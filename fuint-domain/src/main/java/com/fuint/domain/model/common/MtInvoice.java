package com.fuint.domain.model.common;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 发票实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long userId;

    private Long orderId;

    private String orderSn;

    private LocalDateTime InvoiceTime;

    private BigDecimal invoiceAmount;

    private String title;

    private String downloadUrl;

    private String type;

    private String taxCode;

    private String bankName;

    private String bankCardNo;

    private String bankCardName;

    private String description;

    private String email;

    private String mobile;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
