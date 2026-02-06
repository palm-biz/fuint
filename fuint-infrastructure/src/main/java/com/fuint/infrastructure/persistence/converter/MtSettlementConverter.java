package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtSettlement;
import com.fuint.infrastructure.persistence.entity.MtSettlementDO;
import org.springframework.stereotype.Component;

/**
 * MtSettlement 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtSettlementConverter {

    /**
     * DO -> Domain Entity
     */
    public MtSettlement toDomain(MtSettlementDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtSettlement entity = new MtSettlement();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setSettlementNo(dataObject.getSettlementNo());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setSettleRate(dataObject.getSettleRate());
        entity.setTotalOrderAmount(dataObject.getTotalOrderAmount());
        entity.setAmount(dataObject.getAmount());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setDescription(dataObject.getDescription());
        entity.setOperator(dataObject.getOperator());
        entity.setPayStatus(dataObject.getPayStatus());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtSettlementDO toDataObject(MtSettlement entity) {
        if (entity == null) {
            return null;
        }

        MtSettlementDO dataObject = new MtSettlementDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setSettlementNo(entity.getSettlementNo());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setSettleRate(entity.getSettleRate());
        dataObject.setTotalOrderAmount(entity.getTotalOrderAmount());
        dataObject.setAmount(entity.getAmount());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setDescription(entity.getDescription());
        dataObject.setOperator(entity.getOperator());
        dataObject.setPayStatus(entity.getPayStatus());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
