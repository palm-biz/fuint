package com.fuint.domain.model.store;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 店铺员工表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long category;

    private Long userId;

    private String mobile;

    private String realName;

    private String wechat;

    private Long merchantId;

    private Long storeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String auditedStatus;

    private LocalDateTime auditedTime;

    private String description;

}
