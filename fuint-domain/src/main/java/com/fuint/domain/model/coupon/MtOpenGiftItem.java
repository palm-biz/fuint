package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 开卡赠礼明细表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtOpenGiftItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long userId;

    private Long openGiftId;

    private LocalDateTime createTime;

    private String status;


}
