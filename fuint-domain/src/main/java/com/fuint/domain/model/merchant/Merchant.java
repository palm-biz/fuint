package com.fuint.domain.model.merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 商户聚合根 (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {

    /**
     * 商户 ID
     */
    private Long id;

    /**
     * 租户 ID (关联租户)
     */
    private Long tenantId;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * LOGO
     */
    private String logo;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private MerchantStatus status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 激活商户
     */
    public void activate() {
        this.status = MerchantStatus.ACTIVE;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 禁用商户
     */
    public void disable() {
        this.status = MerchantStatus.DISABLED;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 是否激活
     */
    public boolean isActive() {
        return this.status == MerchantStatus.ACTIVE;
    }

    /**
     * 更新商户信息
     */
    public void update(String name, String contact, String phone, String description) {
        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.description = description;
        this.updateTime = LocalDateTime.now();
    }
}
