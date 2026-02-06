package com.fuint.domain.model.user;



import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员分组
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String name;

    private Long merchantId;

    private Long storeId;

    private Long parentId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String description;

    private String operator;
}
