package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 分佣提现记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_commission_cash")
public class MtCommissionCashDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private String settleNo;

    private String uuid;

    private Long merchantId;

    private Long storeId;

    private Long userId;

    private Long staffId;

    private BigDecimal amount;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
