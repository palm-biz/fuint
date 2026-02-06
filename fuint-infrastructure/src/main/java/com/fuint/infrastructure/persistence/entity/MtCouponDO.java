package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 卡券信息表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_coupon")
public class MtCouponDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long groupId;

    private Long merchantId;

    private Long storeId;

    private String type;

    private Long content;

    private String name;

    private Boolean isGive;

    private Long point;

    private String applyGoods;

    private String receiveCode;

    private String useFor;

    private String expireType;

    private Long expireTime;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private BigDecimal amount;

    private String sendWay;

    private Long sendNum;

    private Long total;

    private Long limitNum;

    private String exceptTime;

    private String storeIds;

    private String gradeIds;

    private String description;

    private String image;

    private String remarks;

    private String inRule;

    private String outRule;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;


}
