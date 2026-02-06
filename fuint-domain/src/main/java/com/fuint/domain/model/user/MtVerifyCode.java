package com.fuint.domain.model.user;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 短信验证码表
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtVerifyCode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private String mobile;

    private String verifyCode;

    private LocalDateTime addTime;

    private LocalDateTime expireTime;

    private LocalDateTime usedTime;

    private String validFlag;


}
