package com.fuint.domain.model.common;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 首页banner
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String title;

    private Long merchantId;

    private Long storeId;

    private String url;

    private String image;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private Long sort;

    private String status;

}
