package com.fuint.domain.model.goods;


import java.io.Serializable;
import lombok.Data;

/**
 * 规格表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtGoodsSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long goodsId;

    private String name;

    private String value;

    private String status;


}
