package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtGoodsSku;
import com.fuint.infrastructure.persistence.entity.MtGoodsSkuDO;
import org.springframework.stereotype.Component;

/**
 * MtGoodsSku 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtGoodsSkuConverter {

    /**
     * DO -> Domain Entity
     */
    public MtGoodsSku toDomain(MtGoodsSkuDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtGoodsSku entity = new MtGoodsSku();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setSkuNo(dataObject.getSkuNo());
        entity.setLogo(dataObject.getLogo());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setSpecIds(dataObject.getSpecIds());
        entity.setStock(dataObject.getStock());
        entity.setPrice(dataObject.getPrice());
        entity.setLinePrice(dataObject.getLinePrice());
        entity.setCostPrice(dataObject.getCostPrice());
        entity.setWeight(dataObject.getWeight());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtGoodsSkuDO toDataObject(MtGoodsSku entity) {
        if (entity == null) {
            return null;
        }

        MtGoodsSkuDO dataObject = new MtGoodsSkuDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setSkuNo(entity.getSkuNo());
        dataObject.setLogo(entity.getLogo());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setSpecIds(entity.getSpecIds());
        dataObject.setStock(entity.getStock());
        dataObject.setPrice(entity.getPrice());
        dataObject.setLinePrice(entity.getLinePrice());
        dataObject.setCostPrice(entity.getCostPrice());
        dataObject.setWeight(entity.getWeight());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
