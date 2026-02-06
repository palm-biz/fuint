package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtOpenGift;
import com.fuint.infrastructure.persistence.entity.MtOpenGiftDO;
import org.springframework.stereotype.Component;

/**
 * MtOpenGift 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtOpenGiftConverter {

    /**
     * DO -> Domain Entity
     */
    public MtOpenGift toDomain(MtOpenGiftDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtOpenGift entity = new MtOpenGift();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setGradeId(dataObject.getGradeId());
        entity.setPoint(dataObject.getPoint());
        entity.setCouponId(dataObject.getCouponId());
        entity.setCouponNum(dataObject.getCouponNum());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtOpenGiftDO toDataObject(MtOpenGift entity) {
        if (entity == null) {
            return null;
        }

        MtOpenGiftDO dataObject = new MtOpenGiftDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setGradeId(entity.getGradeId());
        dataObject.setPoint(entity.getPoint());
        dataObject.setCouponId(entity.getCouponId());
        dataObject.setCouponNum(entity.getCouponNum());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
