package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 转赠记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_give")
public class MtGiveDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private Long giveUserId;

    private String mobile;

    private String userMobile;

    private String groupIds;

    private String groupNames;

    private String couponIds;

    private String couponNames;

    private Long num;

    private BigDecimal money;

    private String note;

    private String message;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;


}
