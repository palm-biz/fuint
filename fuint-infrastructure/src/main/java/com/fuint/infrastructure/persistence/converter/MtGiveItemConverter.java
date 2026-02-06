package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtGiveItem;
import com.fuint.infrastructure.persistence.entity.MtGiveItemDO;
import org.springframework.stereotype.Component;

/**
 * MtGiveItem 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtGiveItemConverter {

    /**
     * DO -> Domain Entity
     */
    public MtGiveItem toDomain(MtGiveItemDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtGiveItem entity = new MtGiveItem();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setGiveId(dataObject.getGiveId());
        entity.setUserCouponId(dataObject.getUserCouponId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtGiveItemDO toDataObject(MtGiveItem entity) {
        if (entity == null) {
            return null;
        }

        MtGiveItemDO dataObject = new MtGiveItemDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setGiveId(entity.getGiveId());
        dataObject.setUserCouponId(entity.getUserCouponId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
