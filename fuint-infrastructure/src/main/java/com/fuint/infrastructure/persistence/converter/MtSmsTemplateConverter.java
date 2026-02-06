package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtSmsTemplate;
import com.fuint.infrastructure.persistence.entity.MtSmsTemplateDO;
import org.springframework.stereotype.Component;

/**
 * MtSmsTemplate 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtSmsTemplateConverter {

    /**
     * DO -> Domain Entity
     */
    public MtSmsTemplate toDomain(MtSmsTemplateDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtSmsTemplate entity = new MtSmsTemplate();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setName(dataObject.getName());
        entity.setUname(dataObject.getUname());
        entity.setCode(dataObject.getCode());
        entity.setContent(dataObject.getContent());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtSmsTemplateDO toDataObject(MtSmsTemplate entity) {
        if (entity == null) {
            return null;
        }

        MtSmsTemplateDO dataObject = new MtSmsTemplateDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setName(entity.getName());
        dataObject.setUname(entity.getUname());
        dataObject.setCode(entity.getCode());
        dataObject.setContent(entity.getContent());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
