package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 会员等级表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_user_grade")
public class MtUserGradeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long merchantId;

    private Long grade;

    private String name;

    private String catchCondition;

    private String catchType;

    private BigDecimal catchValue;

    private String userPrivilege;

    private Long validDay;

    private Float discount;

    private Float speedPoint;

    private Float rebate;

    private String status;

}
