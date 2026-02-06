package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtGoodsCate;
import com.fuint.infrastructure.persistence.entity.MtGoodsCateDO;
import org.springframework.stereotype.Component;

/**
 * MtGoodsCate 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtGoodsCateConverter {

    /**
     * DO -> Domain Entity
     */
    public MtGoodsCate toDomain(MtGoodsCateDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtGoodsCate entity = new MtGoodsCate();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setName(dataObject.getName());
        entity.setLogo(dataObject.getLogo());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setSort(dataObject.getSort());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtGoodsCateDO toDataObject(MtGoodsCate entity) {
        if (entity == null) {
            return null;
        }

        MtGoodsCateDO dataObject = new MtGoodsCateDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setName(entity.getName());
        dataObject.setLogo(entity.getLogo());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setSort(entity.getSort());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
