package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtCouponGroup;
import com.fuint.infrastructure.persistence.entity.MtCouponGroupDO;
import org.springframework.stereotype.Component;

/**
 * MtCouponGroup 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCouponGroupConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCouponGroup toDomain(MtCouponGroupDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCouponGroup entity = new MtCouponGroup();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setName(dataObject.getName());
        entity.setMoney(dataObject.getMoney());
        entity.setNum(dataObject.getNum());
        entity.setTotal(dataObject.getTotal());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCouponGroupDO toDataObject(MtCouponGroup entity) {
        if (entity == null) {
            return null;
        }

        MtCouponGroupDO dataObject = new MtCouponGroupDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setName(entity.getName());
        dataObject.setMoney(entity.getMoney());
        dataObject.setNum(entity.getNum());
        dataObject.setTotal(entity.getTotal());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
