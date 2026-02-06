package com.fuint.domain.model.goods;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 商品分类
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtGoodsCate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String name;

    private String logo;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private Long sort;

    private String status;

}
