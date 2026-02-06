package com.fuint.domain.model.booking;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 预约实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String name;

    private Long merchantId;

    private Long storeId;

    private String type;

    private String logo;

    private Long goodsId;

    private Long cateId;

    private String serviceDates;

    private String serviceTimes;

    private String serviceStaffIds;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private Long sort;

    private String status;

}
