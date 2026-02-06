package com.fuint.domain.model.user;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员地址表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long userId;

    private String name;

    private String mobile;

    private Long provinceId;

    private Long cityId;

    private Long regionId;

    private String detail;

    private String isDefault;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

}
