package com.fuint.domain.model.store;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 打印机实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtPrinter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String sn;

    private String name;

    private String autoPrint;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String status;

}
