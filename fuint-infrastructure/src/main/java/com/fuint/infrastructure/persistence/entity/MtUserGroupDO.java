package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.*;

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
@TableName("mt_user_group")
public class MtUserGroupDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
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
