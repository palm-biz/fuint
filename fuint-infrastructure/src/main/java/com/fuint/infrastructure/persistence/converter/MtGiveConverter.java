package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtGive;
import com.fuint.infrastructure.persistence.entity.MtGiveDO;
import org.springframework.stereotype.Component;

/**
 * MtGive 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtGiveConverter {

    /**
     * DO -> Domain Entity
     */
    public MtGive toDomain(MtGiveDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtGive entity = new MtGive();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserId(dataObject.getUserId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setGiveUserId(dataObject.getGiveUserId());
        entity.setMobile(dataObject.getMobile());
        entity.setUserMobile(dataObject.getUserMobile());
        entity.setGroupIds(dataObject.getGroupIds());
        entity.setGroupNames(dataObject.getGroupNames());
        entity.setCouponIds(dataObject.getCouponIds());
        entity.setCouponNames(dataObject.getCouponNames());
        entity.setNum(dataObject.getNum());
        entity.setMoney(dataObject.getMoney());
        entity.setNote(dataObject.getNote());
        entity.setMessage(dataObject.getMessage());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtGiveDO toDataObject(MtGive entity) {
        if (entity == null) {
            return null;
        }

        MtGiveDO dataObject = new MtGiveDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setGiveUserId(entity.getGiveUserId());
        dataObject.setMobile(entity.getMobile());
        dataObject.setUserMobile(entity.getUserMobile());
        dataObject.setGroupIds(entity.getGroupIds());
        dataObject.setGroupNames(entity.getGroupNames());
        dataObject.setCouponIds(entity.getCouponIds());
        dataObject.setCouponNames(entity.getCouponNames());
        dataObject.setNum(entity.getNum());
        dataObject.setMoney(entity.getMoney());
        dataObject.setNote(entity.getNote());
        dataObject.setMessage(entity.getMessage());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
