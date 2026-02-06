package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtGoodsSpec;
import com.fuint.infrastructure.persistence.entity.MtGoodsSpecDO;
import org.springframework.stereotype.Component;

/**
 * MtGoodsSpec 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtGoodsSpecConverter {

    /**
     * DO -> Domain Entity
     */
    public MtGoodsSpec toDomain(MtGoodsSpecDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtGoodsSpec entity = new MtGoodsSpec();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setName(dataObject.getName());
        entity.setValue(dataObject.getValue());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtGoodsSpecDO toDataObject(MtGoodsSpec entity) {
        if (entity == null) {
            return null;
        }

        MtGoodsSpecDO dataObject = new MtGoodsSpecDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setName(entity.getName());
        dataObject.setValue(entity.getValue());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
