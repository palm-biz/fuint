package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.merchant.MtMerchant;
import com.fuint.infrastructure.persistence.entity.MtMerchantDO;
import org.springframework.stereotype.Component;

/**
 * MtMerchant 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtMerchantConverter {

    /**
     * DO -> Domain Entity
     */
    public MtMerchant toDomain(MtMerchantDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtMerchant entity = new MtMerchant();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setType(dataObject.getType());
        entity.setWxAppId(dataObject.getWxAppId());
        entity.setWxAppSecret(dataObject.getWxAppSecret());
        entity.setWxOfficialAppId(dataObject.getWxOfficialAppId());
        entity.setWxOfficialAppSecret(dataObject.getWxOfficialAppSecret());
        entity.setSettleRate(dataObject.getSettleRate());
        entity.setNo(dataObject.getNo());
        entity.setName(dataObject.getName());
        entity.setLogo(dataObject.getLogo());
        entity.setContact(dataObject.getContact());
        entity.setPhone(dataObject.getPhone());
        entity.setAddress(dataObject.getAddress());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtMerchantDO toDataObject(MtMerchant entity) {
        if (entity == null) {
            return null;
        }

        MtMerchantDO dataObject = new MtMerchantDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setType(entity.getType());
        dataObject.setWxAppId(entity.getWxAppId());
        dataObject.setWxAppSecret(entity.getWxAppSecret());
        dataObject.setWxOfficialAppId(entity.getWxOfficialAppId());
        dataObject.setWxOfficialAppSecret(entity.getWxOfficialAppSecret());
        dataObject.setSettleRate(entity.getSettleRate());
        dataObject.setNo(entity.getNo());
        dataObject.setName(entity.getName());
        dataObject.setLogo(entity.getLogo());
        dataObject.setContact(entity.getContact());
        dataObject.setPhone(entity.getPhone());
        dataObject.setAddress(entity.getAddress());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
