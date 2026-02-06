package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtOrder;
import com.fuint.infrastructure.persistence.entity.MtOrderDO;
import org.springframework.stereotype.Component;

/**
 * MtOrder 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtOrderConverter {

    /**
     * DO -> Domain Entity
     */
    public MtOrder toDomain(MtOrderDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtOrder entity = new MtOrder();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setType(dataObject.getType());
        entity.setPayType(dataObject.getPayType());
        entity.setOrderMode(dataObject.getOrderMode());
        entity.setPlatform(dataObject.getPlatform());
        entity.setOrderSn(dataObject.getOrderSn());
        entity.setCouponId(dataObject.getCouponId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setUserId(dataObject.getUserId());
        entity.setVerifyCode(dataObject.getVerifyCode());
        entity.setIsVisitor(dataObject.getIsVisitor());
        entity.setAmount(dataObject.getAmount());
        entity.setPayAmount(dataObject.getPayAmount());
        entity.setSettleStatus(dataObject.getSettleStatus());
        entity.setUsePoint(dataObject.getUsePoint());
        entity.setPointAmount(dataObject.getPointAmount());
        entity.setDiscount(dataObject.getDiscount());
        entity.setDeliveryFee(dataObject.getDeliveryFee());
        entity.setParam(dataObject.getParam());
        entity.setExpressInfo(dataObject.getExpressInfo());
        entity.setRemark(dataObject.getRemark());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setPayTime(dataObject.getPayTime());
        entity.setPayStatus(dataObject.getPayStatus());
        entity.setStaffId(dataObject.getStaffId());
        entity.setConfirmStatus(dataObject.getConfirmStatus());
        entity.setConfirmTime(dataObject.getConfirmTime());
        entity.setConfirmRemark(dataObject.getConfirmRemark());
        entity.setCommissionUserId(dataObject.getCommissionUserId());
        entity.setCommissionStatus(dataObject.getCommissionStatus());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtOrderDO toDataObject(MtOrder entity) {
        if (entity == null) {
            return null;
        }

        MtOrderDO dataObject = new MtOrderDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setType(entity.getType());
        dataObject.setPayType(entity.getPayType());
        dataObject.setOrderMode(entity.getOrderMode());
        dataObject.setPlatform(entity.getPlatform());
        dataObject.setOrderSn(entity.getOrderSn());
        dataObject.setCouponId(entity.getCouponId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setVerifyCode(entity.getVerifyCode());
        dataObject.setIsVisitor(entity.getIsVisitor());
        dataObject.setAmount(entity.getAmount());
        dataObject.setPayAmount(entity.getPayAmount());
        dataObject.setSettleStatus(entity.getSettleStatus());
        dataObject.setUsePoint(entity.getUsePoint());
        dataObject.setPointAmount(entity.getPointAmount());
        dataObject.setDiscount(entity.getDiscount());
        dataObject.setDeliveryFee(entity.getDeliveryFee());
        dataObject.setParam(entity.getParam());
        dataObject.setExpressInfo(entity.getExpressInfo());
        dataObject.setRemark(entity.getRemark());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setPayTime(entity.getPayTime());
        dataObject.setPayStatus(entity.getPayStatus());
        dataObject.setStaffId(entity.getStaffId());
        dataObject.setConfirmStatus(entity.getConfirmStatus());
        dataObject.setConfirmTime(entity.getConfirmTime());
        dataObject.setConfirmRemark(entity.getConfirmRemark());
        dataObject.setCommissionUserId(entity.getCommissionUserId());
        dataObject.setCommissionStatus(entity.getCommissionStatus());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
