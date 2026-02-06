package com.fuint.domain.model.order;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 购物车
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private String isVisitor;

    private String hangNo;

    private Long skuId;

    private Long goodsId;

    private Double num;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;
}
