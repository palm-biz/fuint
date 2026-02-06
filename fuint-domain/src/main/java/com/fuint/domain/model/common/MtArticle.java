package com.fuint.domain.model.common;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文章
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String title;

    private String brief;

    private Long merchantId;

    private Long storeId;

    private String url;

    private String image;

    private String description;

    private Long click;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private Long sort;

    private String status;

}
