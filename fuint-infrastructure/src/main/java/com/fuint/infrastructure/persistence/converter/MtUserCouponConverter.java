package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtUserCoupon;
import com.fuint.infrastructure.persistence.entity.MtUserCouponDO;
import org.springframework.stereotype.Component;

/**
 * MtUserCoupon 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUserCouponConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUserCoupon toDomain(MtUserCouponDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUserCoupon entity = new MtUserCoupon();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setCode(dataObject.getCode());
        entity.setType(dataObject.getType());
        entity.setImage(dataObject.getImage());
        entity.setGroupId(dataObject.getGroupId());
        entity.setCouponId(dataObject.getCouponId());
        entity.setMobile(dataObject.getMobile());
        entity.setUserId(dataObject.getUserId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setAmount(dataObject.getAmount());
        entity.setBalance(dataObject.getBalance());
        entity.setStatus(dataObject.getStatus());
        entity.setUsedTime(dataObject.getUsedTime());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setExpireTime(dataObject.getExpireTime());
        entity.setOperator(dataObject.getOperator());
        entity.setUuid(dataObject.getUuid());
        entity.setOrderId(dataObject.getOrderId());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUserCouponDO toDataObject(MtUserCoupon entity) {
        if (entity == null) {
            return null;
        }

        MtUserCouponDO dataObject = new MtUserCouponDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setCode(entity.getCode());
        dataObject.setType(entity.getType());
        dataObject.setImage(entity.getImage());
        dataObject.setGroupId(entity.getGroupId());
        dataObject.setCouponId(entity.getCouponId());
        dataObject.setMobile(entity.getMobile());
        dataObject.setUserId(entity.getUserId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setAmount(entity.getAmount());
        dataObject.setBalance(entity.getBalance());
        dataObject.setStatus(entity.getStatus());
        dataObject.setUsedTime(entity.getUsedTime());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setExpireTime(entity.getExpireTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setUuid(entity.getUuid());
        dataObject.setOrderId(entity.getOrderId());

        return dataObject;
    }
}
