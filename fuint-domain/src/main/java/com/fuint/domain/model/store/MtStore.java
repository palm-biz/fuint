package com.fuint.domain.model.store;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 店铺表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtStore implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private String name;

    private String logo;

    private String qrCode;

    private String isDefault;

    private String contact;

    private String wxMchId;

    private String wxApiV2;

    private String wxCertPath;

    private String alipayAppId;

    private String alipayPrivateKey;

    private String alipayPublicKey;

    private String phone;

    private String address;

    private String latitude;

    private String longitude;

    private BigDecimal distance;

    private String hours;

    private String license;

    private String creditCode;

    private String bankName;

    private String bankCardName;

    private String bankCardNo;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String operator;

}
