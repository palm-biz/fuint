package com.fuint.domain.model.goods;


import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商品SKU表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtGoodsSku implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String skuNo;

    private String logo;

    private Long goodsId;

    private String specIds;

    private Double stock;

    private BigDecimal price;

    private BigDecimal linePrice;

    private BigDecimal costPrice;

    private BigDecimal weight;

    private String status;

}
