package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.merchant.Merchant;
import com.fuint.domain.model.merchant.MerchantStatus;
import com.fuint.infrastructure.persistence.entity.MerchantDO;
import org.springframework.stereotype.Component;

/**
 * 商户转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MerchantConverter {

    /**
     * DO -> Domain Entity
     */
    public Merchant toDomain(MerchantDO merchantDO) {
        if (merchantDO == null) {
            return null;
        }

        Merchant merchant = new Merchant();
        merchant.setId(merchantDO.getId());
        merchant.setTenantId(merchantDO.getTenantId());
        merchant.setName(merchantDO.getName());
        merchant.setContact(merchantDO.getContact());
        merchant.setPhone(merchantDO.getPhone());
        merchant.setLogo(merchantDO.getLogo());
        merchant.setDescription(merchantDO.getDescription());
        merchant.setStatus(MerchantStatus.fromCode(merchantDO.getStatus()));
        merchant.setCreateTime(merchantDO.getCreateTime());
        merchant.setUpdateTime(merchantDO.getUpdateTime());

        return merchant;
    }

    /**
     * Domain Entity -> DO
     */
    public MerchantDO toDataObject(Merchant merchant) {
        if (merchant == null) {
            return null;
        }

        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setId(merchant.getId());
        merchantDO.setTenantId(merchant.getTenantId());
        merchantDO.setName(merchant.getName());
        merchantDO.setContact(merchant.getContact());
        merchantDO.setPhone(merchant.getPhone());
        merchantDO.setLogo(merchant.getLogo());
        merchantDO.setDescription(merchant.getDescription());
        merchantDO.setStatus(merchant.getStatus() != null ? merchant.getStatus().getCode() : "A");
        merchantDO.setCreateTime(merchant.getCreateTime());
        merchantDO.setUpdateTime(merchant.getUpdateTime());

        return merchantDO;
    }
}
