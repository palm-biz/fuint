package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 店铺员工表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_staff")
public class MtStaffDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private Long category;

    private Long userId;

    private String mobile;

    private String realName;

    private String wechat;

    private Long merchantId;

    private Long storeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String auditedStatus;

    private LocalDateTime auditedTime;

    private String description;

}
