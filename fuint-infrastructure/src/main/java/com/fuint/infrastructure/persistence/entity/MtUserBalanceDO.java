package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员余额实体
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_user_balance")
public class MtUserBalanceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long userId;

    private BigDecimal amount;

    private LocalDateTime createTime;

    private LocalDateTime expired;

    private Long merchantId;

    private Long storeId;

    private String operator;

    private Long orderId;

    private String remark;

    private String status;

    private LocalDateTime updateTime;

}
