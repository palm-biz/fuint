package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 结算表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_settlement")
public class MtSettlementDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private String settlementNo;

    private Long merchantId;

    private Long storeId;

    private BigDecimal settleRate;

    private BigDecimal totalOrderAmount;

    private BigDecimal amount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String description;

    private String operator;

    private String payStatus;

    private String status;

}
