package com.fuint.infrastructure.persistence.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("mt_send_log")
public class MtSendLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户 ID (MyBatis Plus 会自动注入)
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
