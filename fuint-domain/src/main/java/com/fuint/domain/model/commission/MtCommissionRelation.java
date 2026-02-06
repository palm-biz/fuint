package com.fuint.domain.model.commission;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员分销关系表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCommissionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long userId;

    private String inviteCode;

    private Long subUserId;

    private Long level;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
