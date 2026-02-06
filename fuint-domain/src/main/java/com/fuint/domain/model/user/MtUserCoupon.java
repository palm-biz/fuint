package com.fuint.domain.model.user;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员卡券表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtUserCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String code;

    private String type;

    private String image;

    private Long groupId;

    private Long couponId;

    private String mobile;

    private Long userId;

    private Long merchantId;

    private Long storeId;

    private BigDecimal amount;

    private BigDecimal balance;

    private String status;

    private LocalDateTime usedTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime expireTime;

    private String operator;

    private String uuid;

    private Long orderId;

}
