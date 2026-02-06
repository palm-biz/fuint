package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtArticle;
import com.fuint.infrastructure.persistence.entity.MtArticleDO;
import org.springframework.stereotype.Component;

/**
 * MtArticle 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtArticleConverter {

    /**
     * DO -> Domain Entity
     */
    public MtArticle toDomain(MtArticleDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtArticle entity = new MtArticle();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setTitle(dataObject.getTitle());
        entity.setBrief(dataObject.getBrief());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setUrl(dataObject.getUrl());
        entity.setImage(dataObject.getImage());
        entity.setDescription(dataObject.getDescription());
        entity.setClick(dataObject.getClick());
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
    public MtArticleDO toDataObject(MtArticle entity) {
        if (entity == null) {
            return null;
        }

        MtArticleDO dataObject = new MtArticleDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setTitle(entity.getTitle());
        dataObject.setBrief(entity.getBrief());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setUrl(entity.getUrl());
        dataObject.setImage(entity.getImage());
        dataObject.setDescription(entity.getDescription());
        dataObject.setClick(entity.getClick());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setSort(entity.getSort());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
