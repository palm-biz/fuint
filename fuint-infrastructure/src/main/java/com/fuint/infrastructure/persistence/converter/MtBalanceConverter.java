package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtBalance;
import com.fuint.infrastructure.persistence.entity.MtBalanceDO;
import org.springframework.stereotype.Component;

/**
 * MtBalance 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtBalanceConverter {

    /**
     * DO -> Domain Entity
     */
    public MtBalance toDomain(MtBalanceDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtBalance entity = new MtBalance();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setMobile(dataObject.getMobile());
        entity.setUserId(dataObject.getUserId());
        entity.setOrderSn(dataObject.getOrderSn());
        entity.setAmount(dataObject.getAmount());
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
    public MtBalanceDO toDataObject(MtBalance entity) {
        if (entity == null) {
            return null;
        }

        MtBalanceDO dataObject = new MtBalanceDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setMobile(entity.getMobile());
        dataObject.setUserId(entity.getUserId());
        dataObject.setOrderSn(entity.getOrderSn());
        dataObject.setAmount(entity.getAmount());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setDescription(entity.getDescription());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
