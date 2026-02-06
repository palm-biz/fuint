package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 佣金记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_commission_log")
public class MtCommissionLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private String target;

    private String type;

    private Long level;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private Long staffId;

    private Long orderId;

    private BigDecimal amount;

    private Long ruleId;

    private Long ruleItemId;

    private String description;

    private String settleUuid;

    private Long cashId;

    private String isCash;

    private LocalDateTime cashTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
