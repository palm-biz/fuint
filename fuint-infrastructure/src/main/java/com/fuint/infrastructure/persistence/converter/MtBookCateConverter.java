package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.booking.MtBookCate;
import com.fuint.infrastructure.persistence.entity.MtBookCateDO;
import org.springframework.stereotype.Component;

/**
 * MtBookCate 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtBookCateConverter {

    /**
     * DO -> Domain Entity
     */
    public MtBookCate toDomain(MtBookCateDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtBookCate entity = new MtBookCate();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setName(dataObject.getName());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
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
    public MtBookCateDO toDataObject(MtBookCate entity) {
        if (entity == null) {
            return null;
        }

        MtBookCateDO dataObject = new MtBookCateDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setName(entity.getName());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
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
