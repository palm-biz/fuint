package com.fuint.domain.model.user;



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
public class MtUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

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
