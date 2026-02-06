package com.fuint.domain.model.common;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 短信模板
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtSmsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String name;

    private String uname;

    private String code;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
