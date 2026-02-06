package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.commission.MtCommissionRelation;
import com.fuint.infrastructure.persistence.entity.MtCommissionRelationDO;
import org.springframework.stereotype.Component;

/**
 * MtCommissionRelation 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCommissionRelationConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCommissionRelation toDomain(MtCommissionRelationDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCommissionRelation entity = new MtCommissionRelation();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setUserId(dataObject.getUserId());
        entity.setInviteCode(dataObject.getInviteCode());
        entity.setSubUserId(dataObject.getSubUserId());
        entity.setLevel(dataObject.getLevel());
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
    public MtCommissionRelationDO toDataObject(MtCommissionRelation entity) {
        if (entity == null) {
            return null;
        }

        MtCommissionRelationDO dataObject = new MtCommissionRelationDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setInviteCode(entity.getInviteCode());
        dataObject.setSubUserId(entity.getSubUserId());
        dataObject.setLevel(entity.getLevel());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
