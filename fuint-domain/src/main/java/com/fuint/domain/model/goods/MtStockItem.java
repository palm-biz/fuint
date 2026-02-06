package com.fuint.domain.model.goods;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 库存管理明细表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtStockItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long stockId;

    private Long goodsId;

    private Long skuId;

    private Double num;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

}
