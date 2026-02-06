package com.fuint.domain.model.user;


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
public class MtUserGrade implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
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
