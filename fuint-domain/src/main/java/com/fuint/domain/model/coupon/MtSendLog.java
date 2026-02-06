package com.fuint.domain.model.coupon;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 卡券发放记录表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtSendLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long storeId;

    private Long type;

    private Long userId;

    private String fileName;

    private String filePath;

    private String mobile;

    private Long groupId;

    private String groupName;

    private Long couponId;

    private Long sendNum;

    private LocalDateTime createTime;

    private String operator;

    private String uuid;

    private Long removeSuccessNum;

    private Long removeFailNum;

    private String status;


}
