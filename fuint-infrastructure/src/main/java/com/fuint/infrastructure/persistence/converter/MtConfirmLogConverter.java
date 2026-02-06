package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtConfirmLog;
import com.fuint.infrastructure.persistence.entity.MtConfirmLogDO;
import org.springframework.stereotype.Component;

/**
 * MtConfirmLog 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtConfirmLogConverter {

    /**
     * DO -> Domain Entity
     */
    public MtConfirmLog toDomain(MtConfirmLogDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtConfirmLog entity = new MtConfirmLog();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setCode(dataObject.getCode());
        entity.setAmount(dataObject.getAmount());
        entity.setCouponId(dataObject.getCouponId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setUserCouponId(dataObject.getUserCouponId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setUserId(dataObject.getUserId());
        entity.setOperatorUserId(dataObject.getOperatorUserId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setStatus(dataObject.getStatus());
        entity.setCancelTime(dataObject.getCancelTime());
        entity.setOperator(dataObject.getOperator());
        entity.setOperatorFrom(dataObject.getOperatorFrom());
        entity.setRemark(dataObject.getRemark());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtConfirmLogDO toDataObject(MtConfirmLog entity) {
        if (entity == null) {
            return null;
        }

        MtConfirmLogDO dataObject = new MtConfirmLogDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setCode(entity.getCode());
        dataObject.setAmount(entity.getAmount());
        dataObject.setCouponId(entity.getCouponId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setUserCouponId(entity.getUserCouponId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setUserId(entity.getUserId());
        dataObject.setOperatorUserId(entity.getOperatorUserId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setStatus(entity.getStatus());
        dataObject.setCancelTime(entity.getCancelTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setOperatorFrom(entity.getOperatorFrom());
        dataObject.setRemark(entity.getRemark());

        return dataObject;
    }
}
