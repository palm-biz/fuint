package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.store.MtPrinter;
import com.fuint.infrastructure.persistence.entity.MtPrinterDO;
import org.springframework.stereotype.Component;

/**
 * MtPrinter 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtPrinterConverter {

    /**
     * DO -> Domain Entity
     */
    public MtPrinter toDomain(MtPrinterDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtPrinter entity = new MtPrinter();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setSn(dataObject.getSn());
        entity.setName(dataObject.getName());
        entity.setAutoPrint(dataObject.getAutoPrint());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setDescription(dataObject.getDescription());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtPrinterDO toDataObject(MtPrinter entity) {
        if (entity == null) {
            return null;
        }

        MtPrinterDO dataObject = new MtPrinterDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setSn(entity.getSn());
        dataObject.setName(entity.getName());
        dataObject.setAutoPrint(entity.getAutoPrint());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setDescription(entity.getDescription());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
