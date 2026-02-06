package com.fuint.domain.model.user;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员行为信息
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtUserAction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private String action;

    private String description;

    private String param;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String operator;
}
