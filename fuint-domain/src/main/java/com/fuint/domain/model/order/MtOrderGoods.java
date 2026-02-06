package com.fuint.domain.model.order;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 订单商品表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtOrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long orderId;

    private Long goodsId;

    private Long skuId;

    private BigDecimal price;

    private BigDecimal discount;

    private Double num;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

}
