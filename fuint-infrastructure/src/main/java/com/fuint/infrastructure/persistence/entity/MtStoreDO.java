package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("mt_store")
public class MtStoreDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
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
