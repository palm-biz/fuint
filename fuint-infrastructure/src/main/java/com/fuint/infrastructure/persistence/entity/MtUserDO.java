package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 会员个人信息
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
@TableName("mt_user")
public class MtUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
     */
    private Long tenantId;

    private String userNo;

    private String avatar;

    private Long groupId;

    private String name;

    private String openId;

    private String mobile;

    private String idcard;

    private Long gradeId;

    @TableField(strategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @TableField(strategy=FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    private BigDecimal balance;

    private Long point;

    private Long sex;

    private String birthday;

    private String carNo;

    private String source;

    private String password;

    private String salt;

    private String address;

    private Long merchantId;

    private Long storeId;

    private String isStaff;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    private String description;

    private String ip;

    private String operator;
}
