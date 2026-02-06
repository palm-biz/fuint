package com.fuint.domain.model.merchant;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 商户表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtMerchant implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String type;

    private String wxAppId;

    private String wxAppSecret;

    private String wxOfficialAppId;

    private String wxOfficialAppSecret;

    private BigDecimal settleRate;

    private String no;

    private String name;

    private String logo;

    private String contact;

    private String phone;

    private String address;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String operator;

}
