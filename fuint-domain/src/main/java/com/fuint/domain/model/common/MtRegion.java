package com.fuint.domain.model.common;


import java.io.Serializable;
import lombok.Data;

/**
 * 省市区数据表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String name;

    private Long pid;

    private String code;

    private Long level;


}
