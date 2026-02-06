package com.fuint.domain.model.commission;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 分佣提成规则项目表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtCommissionRuleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String type;

    private String target;

    private Long ruleId;

    private Long merchantId;

    private Long storeId;

    private Long targetId;

    private String method;

    private String storeIds;

    private BigDecimal guest;

    private BigDecimal subGuest;

    private BigDecimal member;

    private BigDecimal subMember;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String operator;

    private String status;

}
