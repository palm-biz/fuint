package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtStockItem;
import com.fuint.infrastructure.persistence.entity.MtStockItemDO;
import org.springframework.stereotype.Component;

/**
 * MtStockItem 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtStockItemConverter {

    /**
     * DO -> Domain Entity
     */
    public MtStockItem toDomain(MtStockItemDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtStockItem entity = new MtStockItem();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setStockId(dataObject.getStockId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setSkuId(dataObject.getSkuId());
        entity.setNum(dataObject.getNum());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtStockItemDO toDataObject(MtStockItem entity) {
        if (entity == null) {
            return null;
        }

        MtStockItemDO dataObject = new MtStockItemDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setStockId(entity.getStockId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setSkuId(entity.getSkuId());
        dataObject.setNum(entity.getNum());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
