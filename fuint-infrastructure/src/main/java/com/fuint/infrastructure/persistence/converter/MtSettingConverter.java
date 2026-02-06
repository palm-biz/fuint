package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtSetting;
import com.fuint.infrastructure.persistence.entity.MtSettingDO;
import org.springframework.stereotype.Component;

/**
 * MtSetting 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtSettingConverter {

    /**
     * DO -> Domain Entity
     */
    public MtSetting toDomain(MtSettingDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtSetting entity = new MtSetting();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setType(dataObject.getType());
        entity.setName(dataObject.getName());
        entity.setValue(dataObject.getValue());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtSettingDO toDataObject(MtSetting entity) {
        if (entity == null) {
            return null;
        }

        MtSettingDO dataObject = new MtSettingDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setType(entity.getType());
        dataObject.setName(entity.getName());
        dataObject.setValue(entity.getValue());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
