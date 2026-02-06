package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 核销记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_confirm_log")
public class MtConfirmLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private String code;

    private BigDecimal amount;

    private Long couponId;

    private Long merchantId;

    private Long userCouponId;

    private Long orderId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long userId;

    private Long operatorUserId;

    private Long storeId;

    private String status;

    private LocalDateTime cancelTime;

    private String operator;

    private String operatorFrom;

    private String remark;


}
