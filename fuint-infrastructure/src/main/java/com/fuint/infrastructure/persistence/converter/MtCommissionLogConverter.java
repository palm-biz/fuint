package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.commission.MtCommissionLog;
import com.fuint.infrastructure.persistence.entity.MtCommissionLogDO;
import org.springframework.stereotype.Component;

/**
 * MtCommissionLog 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCommissionLogConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCommissionLog toDomain(MtCommissionLogDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCommissionLog entity = new MtCommissionLog();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setTarget(dataObject.getTarget());
        entity.setType(dataObject.getType());
        entity.setLevel(dataObject.getLevel());
        entity.setUserId(dataObject.getUserId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setStaffId(dataObject.getStaffId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setAmount(dataObject.getAmount());
        entity.setRuleId(dataObject.getRuleId());
        entity.setRuleItemId(dataObject.getRuleItemId());
        entity.setDescription(dataObject.getDescription());
        entity.setSettleUuid(dataObject.getSettleUuid());
        entity.setCashId(dataObject.getCashId());
        entity.setIsCash(dataObject.getIsCash());
        entity.setCashTime(dataObject.getCashTime());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCommissionLogDO toDataObject(MtCommissionLog entity) {
        if (entity == null) {
            return null;
        }

        MtCommissionLogDO dataObject = new MtCommissionLogDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setTarget(entity.getTarget());
        dataObject.setType(entity.getType());
        dataObject.setLevel(entity.getLevel());
        dataObject.setUserId(entity.getUserId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setStaffId(entity.getStaffId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setAmount(entity.getAmount());
        dataObject.setRuleId(entity.getRuleId());
        dataObject.setRuleItemId(entity.getRuleItemId());
        dataObject.setDescription(entity.getDescription());
        dataObject.setSettleUuid(entity.getSettleUuid());
        dataObject.setCashId(entity.getCashId());
        dataObject.setIsCash(entity.getIsCash());
        dataObject.setCashTime(entity.getCashTime());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
