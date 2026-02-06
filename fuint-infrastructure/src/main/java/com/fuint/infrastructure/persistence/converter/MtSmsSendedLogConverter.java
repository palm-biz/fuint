package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtSmsSendedLog;
import com.fuint.infrastructure.persistence.entity.MtSmsSendedLogDO;
import org.springframework.stereotype.Component;

/**
 * MtSmsSendedLog 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtSmsSendedLogConverter {

    /**
     * DO -> Domain Entity
     */
    public MtSmsSendedLog toDomain(MtSmsSendedLogDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtSmsSendedLog entity = new MtSmsSendedLog();
        entity.setLogId(dataObject.getLogId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setMobilePhone(dataObject.getMobilePhone());
        entity.setContent(dataObject.getContent());
        entity.setSendTime(dataObject.getSendTime());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtSmsSendedLogDO toDataObject(MtSmsSendedLog entity) {
        if (entity == null) {
            return null;
        }

        MtSmsSendedLogDO dataObject = new MtSmsSendedLogDO();
        dataObject.setLogId(entity.getLogId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setMobilePhone(entity.getMobilePhone());
        dataObject.setContent(entity.getContent());
        dataObject.setSendTime(entity.getSendTime());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());

        return dataObject;
    }
}
