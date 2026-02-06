package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtBanner;
import com.fuint.infrastructure.persistence.entity.MtBannerDO;
import org.springframework.stereotype.Component;

/**
 * MtBanner 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtBannerConverter {

    /**
     * DO -> Domain Entity
     */
    public MtBanner toDomain(MtBannerDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtBanner entity = new MtBanner();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setTitle(dataObject.getTitle());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setUrl(dataObject.getUrl());
        entity.setImage(dataObject.getImage());
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
    public MtBannerDO toDataObject(MtBanner entity) {
        if (entity == null) {
            return null;
        }

        MtBannerDO dataObject = new MtBannerDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setTitle(entity.getTitle());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setUrl(entity.getUrl());
        dataObject.setImage(entity.getImage());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setSort(entity.getSort());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
