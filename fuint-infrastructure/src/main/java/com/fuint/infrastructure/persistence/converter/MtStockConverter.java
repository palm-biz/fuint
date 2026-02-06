package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtStock;
import com.fuint.infrastructure.persistence.entity.MtStockDO;
import org.springframework.stereotype.Component;

/**
 * MtStock 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtStockConverter {

    /**
     * DO -> Domain Entity
     */
    public MtStock toDomain(MtStockDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtStock entity = new MtStock();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setType(dataObject.getType());
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
    public MtStockDO toDataObject(MtStock entity) {
        if (entity == null) {
            return null;
        }

        MtStockDO dataObject = new MtStockDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setType(entity.getType());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
