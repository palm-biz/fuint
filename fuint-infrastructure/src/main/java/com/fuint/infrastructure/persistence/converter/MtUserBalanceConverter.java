package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtUserBalance;
import com.fuint.infrastructure.persistence.entity.MtUserBalanceDO;
import org.springframework.stereotype.Component;

/**
 * MtUserBalance 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUserBalanceConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUserBalance toDomain(MtUserBalanceDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUserBalance entity = new MtUserBalance();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserId(dataObject.getUserId());
        entity.setAmount(dataObject.getAmount());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setExpired(dataObject.getExpired());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setOperator(dataObject.getOperator());
        entity.setOrderId(dataObject.getOrderId());
        entity.setRemark(dataObject.getRemark());
        entity.setStatus(dataObject.getStatus());
        entity.setUpdateTime(dataObject.getUpdateTime());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUserBalanceDO toDataObject(MtUserBalance entity) {
        if (entity == null) {
            return null;
        }

        MtUserBalanceDO dataObject = new MtUserBalanceDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setAmount(entity.getAmount());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setExpired(entity.getExpired());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setOperator(entity.getOperator());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setRemark(entity.getRemark());
        dataObject.setStatus(entity.getStatus());
        dataObject.setUpdateTime(entity.getUpdateTime());

        return dataObject;
    }
}
