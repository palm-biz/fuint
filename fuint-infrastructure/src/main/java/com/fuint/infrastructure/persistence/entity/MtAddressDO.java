package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员地址表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_address")
public class MtAddressDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long userId;

    private String name;

    private String mobile;

    private Long provinceId;

    private Long cityId;

    private Long regionId;

    private String detail;

    private String isDefault;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

}
