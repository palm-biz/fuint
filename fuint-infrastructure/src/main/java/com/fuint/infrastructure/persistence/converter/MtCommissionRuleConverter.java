package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.commission.MtCommissionRule;
import com.fuint.infrastructure.persistence.entity.MtCommissionRuleDO;
import org.springframework.stereotype.Component;

/**
 * MtCommissionRule 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCommissionRuleConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCommissionRule toDomain(MtCommissionRuleDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCommissionRule entity = new MtCommissionRule();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setName(dataObject.getName());
        entity.setType(dataObject.getType());
        entity.setTarget(dataObject.getTarget());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setStoreIds(dataObject.getStoreIds());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setDescription(dataObject.getDescription());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCommissionRuleDO toDataObject(MtCommissionRule entity) {
        if (entity == null) {
            return null;
        }

        MtCommissionRuleDO dataObject = new MtCommissionRuleDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setName(entity.getName());
        dataObject.setType(entity.getType());
        dataObject.setTarget(entity.getTarget());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setStoreIds(entity.getStoreIds());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setDescription(entity.getDescription());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
