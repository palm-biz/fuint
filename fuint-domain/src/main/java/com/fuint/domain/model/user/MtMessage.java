package com.fuint.domain.model.user;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 系统消息
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
@Data
public class MtMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 租户 ID
     */
    private Long tenantId;

    private Long merchantId;

    private Long userId;

    private String type;

    private String title;

    private String content;

    private String isRead;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String params;

    private String isSend;

    private LocalDateTime sendTime;

    private String status;

}
