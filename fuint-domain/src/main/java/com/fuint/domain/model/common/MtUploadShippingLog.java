package com.fuint.domain.model.common;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 微信小程序上传发货信息实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtUploadShippingLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long orderId;

    private String orderSn;

    private String mobile;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
