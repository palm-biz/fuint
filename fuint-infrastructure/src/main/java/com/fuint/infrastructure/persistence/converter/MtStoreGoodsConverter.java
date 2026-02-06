package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtStoreGoods;
import com.fuint.infrastructure.persistence.entity.MtStoreGoodsDO;
import org.springframework.stereotype.Component;

/**
 * MtStoreGoods 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtStoreGoodsConverter {

    /**
     * DO -> Domain Entity
     */
    public MtStoreGoods toDomain(MtStoreGoodsDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtStoreGoods entity = new MtStoreGoods();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtStoreGoodsDO toDataObject(MtStoreGoods entity) {
        if (entity == null) {
            return null;
        }

        MtStoreGoodsDO dataObject = new MtStoreGoodsDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
