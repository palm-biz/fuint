package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtInvoice;
import com.fuint.infrastructure.persistence.entity.MtInvoiceDO;
import org.springframework.stereotype.Component;

/**
 * MtInvoice 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtInvoiceConverter {

    /**
     * DO -> Domain Entity
     */
    public MtInvoice toDomain(MtInvoiceDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtInvoice entity = new MtInvoice();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setUserId(dataObject.getUserId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setOrderSn(dataObject.getOrderSn());
        entity.setInvoiceTime(dataObject.getInvoiceTime());
        entity.setInvoiceAmount(dataObject.getInvoiceAmount());
        entity.setTitle(dataObject.getTitle());
        entity.setDownloadUrl(dataObject.getDownloadUrl());
        entity.setType(dataObject.getType());
        entity.setTaxCode(dataObject.getTaxCode());
        entity.setBankName(dataObject.getBankName());
        entity.setBankCardNo(dataObject.getBankCardNo());
        entity.setBankCardName(dataObject.getBankCardName());
        entity.setDescription(dataObject.getDescription());
        entity.setEmail(dataObject.getEmail());
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
    public MtInvoiceDO toDataObject(MtInvoice entity) {
        if (entity == null) {
            return null;
        }

        MtInvoiceDO dataObject = new MtInvoiceDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setOrderSn(entity.getOrderSn());
        dataObject.setInvoiceTime(entity.getInvoiceTime());
        dataObject.setInvoiceAmount(entity.getInvoiceAmount());
        dataObject.setTitle(entity.getTitle());
        dataObject.setDownloadUrl(entity.getDownloadUrl());
        dataObject.setType(entity.getType());
        dataObject.setTaxCode(entity.getTaxCode());
        dataObject.setBankName(entity.getBankName());
        dataObject.setBankCardNo(entity.getBankCardNo());
        dataObject.setBankCardName(entity.getBankCardName());
        dataObject.setDescription(entity.getDescription());
        dataObject.setEmail(entity.getEmail());
        dataObject.setMobile(entity.getMobile());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
