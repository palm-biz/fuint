package com.fuint.domain.model.common;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 短信发送记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtSmsSendedLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long logId;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private String mobilePhone;

    private String content;

    private LocalDateTime sendTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
