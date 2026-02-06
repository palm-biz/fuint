package com.fuint.domain.model.commission;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 分佣提成规则表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCommissionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String name;

    private String type;

    private String target;

    private Long merchantId;

    private Long storeId;

    private String storeIds;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String status;

}
