package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.store.MtStaff;
import com.fuint.infrastructure.persistence.entity.MtStaffDO;
import org.springframework.stereotype.Component;

/**
 * MtStaff 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtStaffConverter {

    /**
     * DO -> Domain Entity
     */
    public MtStaff toDomain(MtStaffDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtStaff entity = new MtStaff();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setCategory(dataObject.getCategory());
        entity.setUserId(dataObject.getUserId());
        entity.setMobile(dataObject.getMobile());
        entity.setRealName(dataObject.getRealName());
        entity.setWechat(dataObject.getWechat());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setAuditedStatus(dataObject.getAuditedStatus());
        entity.setAuditedTime(dataObject.getAuditedTime());
        entity.setDescription(dataObject.getDescription());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtStaffDO toDataObject(MtStaff entity) {
        if (entity == null) {
            return null;
        }

        MtStaffDO dataObject = new MtStaffDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setCategory(entity.getCategory());
        dataObject.setUserId(entity.getUserId());
        dataObject.setMobile(entity.getMobile());
        dataObject.setRealName(entity.getRealName());
        dataObject.setWechat(entity.getWechat());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setAuditedStatus(entity.getAuditedStatus());
        dataObject.setAuditedTime(entity.getAuditedTime());
        dataObject.setDescription(entity.getDescription());

        return dataObject;
    }
}
