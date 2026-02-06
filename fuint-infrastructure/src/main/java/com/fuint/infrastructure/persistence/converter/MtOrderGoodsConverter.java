package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtOrderGoods;
import com.fuint.infrastructure.persistence.entity.MtOrderGoodsDO;
import org.springframework.stereotype.Component;

/**
 * MtOrderGoods 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtOrderGoodsConverter {

    /**
     * DO -> Domain Entity
     */
    public MtOrderGoods toDomain(MtOrderGoodsDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtOrderGoods entity = new MtOrderGoods();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setSkuId(dataObject.getSkuId());
        entity.setPrice(dataObject.getPrice());
        entity.setDiscount(dataObject.getDiscount());
        entity.setNum(dataObject.getNum());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtOrderGoodsDO toDataObject(MtOrderGoods entity) {
        if (entity == null) {
            return null;
        }

        MtOrderGoodsDO dataObject = new MtOrderGoodsDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setSkuId(entity.getSkuId());
        dataObject.setPrice(entity.getPrice());
        dataObject.setDiscount(entity.getDiscount());
        dataObject.setNum(entity.getNum());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
