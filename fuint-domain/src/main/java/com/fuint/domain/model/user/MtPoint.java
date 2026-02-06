package com.fuint.domain.model.user;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员积分记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long userId;

    private String orderSn;

    private Long amount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String status;

}
