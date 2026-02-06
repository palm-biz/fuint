package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtMessage;
import com.fuint.infrastructure.persistence.entity.MtMessageDO;
import org.springframework.stereotype.Component;

/**
 * MtMessage 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtMessageConverter {

    /**
     * DO -> Domain Entity
     */
    public MtMessage toDomain(MtMessageDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtMessage entity = new MtMessage();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setUserId(dataObject.getUserId());
        entity.setType(dataObject.getType());
        entity.setTitle(dataObject.getTitle());
        entity.setContent(dataObject.getContent());
        entity.setIsRead(dataObject.getIsRead());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setParams(dataObject.getParams());
        entity.setIsSend(dataObject.getIsSend());
        entity.setSendTime(dataObject.getSendTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtMessageDO toDataObject(MtMessage entity) {
        if (entity == null) {
            return null;
        }

        MtMessageDO dataObject = new MtMessageDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setType(entity.getType());
        dataObject.setTitle(entity.getTitle());
        dataObject.setContent(entity.getContent());
        dataObject.setIsRead(entity.getIsRead());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setParams(entity.getParams());
        dataObject.setIsSend(entity.getIsSend());
        dataObject.setSendTime(entity.getSendTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
