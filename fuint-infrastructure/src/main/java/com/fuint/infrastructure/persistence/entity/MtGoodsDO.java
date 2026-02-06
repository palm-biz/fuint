package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_goods")
public class MtGoodsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String name;

    private String type;

    private Long cateId;

    private Long bookId;

    private String goodsNo;

    private Long platform;

    private String isSingleSpec;

    private String logo;

    private String images;

    private BigDecimal price;

    private BigDecimal linePrice;

    private BigDecimal costPrice;

    private Double stock;

    private String couponIds;

    private Long serviceTime;

    private BigDecimal weight;

    private Double initSale;

    private String salePoint;

    private String canUsePoint;

    private String isMemberDiscount;

    private Long sort;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
