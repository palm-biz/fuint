package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.commission.MtCommissionRuleItem;
import com.fuint.infrastructure.persistence.entity.MtCommissionRuleItemDO;
import org.springframework.stereotype.Component;

/**
 * MtCommissionRuleItem 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCommissionRuleItemConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCommissionRuleItem toDomain(MtCommissionRuleItemDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCommissionRuleItem entity = new MtCommissionRuleItem();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setType(dataObject.getType());
        entity.setTarget(dataObject.getTarget());
        entity.setRuleId(dataObject.getRuleId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setTargetId(dataObject.getTargetId());
        entity.setMethod(dataObject.getMethod());
        entity.setStoreIds(dataObject.getStoreIds());
        entity.setGuest(dataObject.getGuest());
        entity.setSubGuest(dataObject.getSubGuest());
        entity.setMember(dataObject.getMember());
        entity.setSubMember(dataObject.getSubMember());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCommissionRuleItemDO toDataObject(MtCommissionRuleItem entity) {
        if (entity == null) {
            return null;
        }

        MtCommissionRuleItemDO dataObject = new MtCommissionRuleItemDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setType(entity.getType());
        dataObject.setTarget(entity.getTarget());
        dataObject.setRuleId(entity.getRuleId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setTargetId(entity.getTargetId());
        dataObject.setMethod(entity.getMethod());
        dataObject.setStoreIds(entity.getStoreIds());
        dataObject.setGuest(entity.getGuest());
        dataObject.setSubGuest(entity.getSubGuest());
        dataObject.setMember(entity.getMember());
        dataObject.setSubMember(entity.getSubMember());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
