package com.fuint.domain.model.booking;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 预约订单实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtBookItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long cateId;

    private Long bookId;

    private Long userId;

    private Long goodsId;

    private String verifyCode;

    private String contact;

    private String mobile;

    private String serviceDate;

    private String serviceTime;

    private String remark;

    private Long serviceStaffId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
