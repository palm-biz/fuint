package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtUserAction;
import com.fuint.infrastructure.persistence.entity.MtUserActionDO;
import org.springframework.stereotype.Component;

/**
 * MtUserAction 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUserActionConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUserAction toDomain(MtUserActionDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUserAction entity = new MtUserAction();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserId(dataObject.getUserId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setAction(dataObject.getAction());
        entity.setDescription(dataObject.getDescription());
        entity.setParam(dataObject.getParam());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUserActionDO toDataObject(MtUserAction entity) {
        if (entity == null) {
            return null;
        }

        MtUserActionDO dataObject = new MtUserActionDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setAction(entity.getAction());
        dataObject.setDescription(entity.getDescription());
        dataObject.setParam(entity.getParam());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
