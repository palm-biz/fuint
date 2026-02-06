package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtRefund;
import com.fuint.infrastructure.persistence.entity.MtRefundDO;
import org.springframework.stereotype.Component;

/**
 * MtRefund 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtRefundConverter {

    /**
     * DO -> Domain Entity
     */
    public MtRefund toDomain(MtRefundDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtRefund entity = new MtRefund();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setUserId(dataObject.getUserId());
        entity.setAmount(dataObject.getAmount());
        entity.setType(dataObject.getType());
        entity.setRemark(dataObject.getRemark());
        entity.setExpressName(dataObject.getExpressName());
        entity.setExpressNo(dataObject.getExpressNo());
        entity.setRejectReason(dataObject.getRejectReason());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setImages(dataObject.getImages());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtRefundDO toDataObject(MtRefund entity) {
        if (entity == null) {
            return null;
        }

        MtRefundDO dataObject = new MtRefundDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setAmount(entity.getAmount());
        dataObject.setType(entity.getType());
        dataObject.setRemark(entity.getRemark());
        dataObject.setExpressName(entity.getExpressName());
        dataObject.setExpressNo(entity.getExpressNo());
        dataObject.setRejectReason(entity.getRejectReason());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setImages(entity.getImages());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
