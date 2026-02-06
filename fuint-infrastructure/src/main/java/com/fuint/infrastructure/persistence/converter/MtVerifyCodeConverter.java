package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtVerifyCode;
import com.fuint.infrastructure.persistence.entity.MtVerifyCodeDO;
import org.springframework.stereotype.Component;

/**
 * MtVerifyCode 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtVerifyCodeConverter {

    /**
     * DO -> Domain Entity
     */
    public MtVerifyCode toDomain(MtVerifyCodeDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtVerifyCode entity = new MtVerifyCode();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMobile(dataObject.getMobile());
        entity.setVerifyCode(dataObject.getVerifyCode());
        entity.setAddTime(dataObject.getAddTime());
        entity.setExpireTime(dataObject.getExpireTime());
        entity.setUsedTime(dataObject.getUsedTime());
        entity.setValidFlag(dataObject.getValidFlag());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtVerifyCodeDO toDataObject(MtVerifyCode entity) {
        if (entity == null) {
            return null;
        }

        MtVerifyCodeDO dataObject = new MtVerifyCodeDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMobile(entity.getMobile());
        dataObject.setVerifyCode(entity.getVerifyCode());
        dataObject.setAddTime(entity.getAddTime());
        dataObject.setExpireTime(entity.getExpireTime());
        dataObject.setUsedTime(entity.getUsedTime());
        dataObject.setValidFlag(entity.getValidFlag());

        return dataObject;
    }
}
