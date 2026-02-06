package com.fuint.domain.model.goods;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 店铺商品实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtStoreGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long goodsId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String operator;

}
