package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtUploadShippingLog;
import com.fuint.infrastructure.persistence.entity.MtUploadShippingLogDO;
import org.springframework.stereotype.Component;

/**
 * MtUploadShippingLog 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUploadShippingLogConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUploadShippingLog toDomain(MtUploadShippingLogDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUploadShippingLog entity = new MtUploadShippingLog();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setOrderSn(dataObject.getOrderSn());
        entity.setMobile(dataObject.getMobile());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUploadShippingLogDO toDataObject(MtUploadShippingLog entity) {
        if (entity == null) {
            return null;
        }

        MtUploadShippingLogDO dataObject = new MtUploadShippingLogDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setOrderSn(entity.getOrderSn());
        dataObject.setMobile(entity.getMobile());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
