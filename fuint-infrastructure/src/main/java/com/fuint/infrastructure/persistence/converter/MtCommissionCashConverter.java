package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.commission.MtCommissionCash;
import com.fuint.infrastructure.persistence.entity.MtCommissionCashDO;
import org.springframework.stereotype.Component;

/**
 * MtCommissionCash 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCommissionCashConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCommissionCash toDomain(MtCommissionCashDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCommissionCash entity = new MtCommissionCash();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setSettleNo(dataObject.getSettleNo());
        entity.setUuid(dataObject.getUuid());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setUserId(dataObject.getUserId());
        entity.setStaffId(dataObject.getStaffId());
        entity.setAmount(dataObject.getAmount());
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
    public MtCommissionCashDO toDataObject(MtCommissionCash entity) {
        if (entity == null) {
            return null;
        }

        MtCommissionCashDO dataObject = new MtCommissionCashDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setSettleNo(entity.getSettleNo());
        dataObject.setUuid(entity.getUuid());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setStaffId(entity.getStaffId());
        dataObject.setAmount(entity.getAmount());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
