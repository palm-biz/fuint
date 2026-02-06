package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtUserGroup;
import com.fuint.infrastructure.persistence.entity.MtUserGroupDO;
import org.springframework.stereotype.Component;

/**
 * MtUserGroup 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUserGroupConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUserGroup toDomain(MtUserGroupDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUserGroup entity = new MtUserGroup();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setName(dataObject.getName());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setParentId(dataObject.getParentId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setDescription(dataObject.getDescription());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUserGroupDO toDataObject(MtUserGroup entity) {
        if (entity == null) {
            return null;
        }

        MtUserGroupDO dataObject = new MtUserGroupDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setName(entity.getName());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setParentId(entity.getParentId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setDescription(entity.getDescription());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
