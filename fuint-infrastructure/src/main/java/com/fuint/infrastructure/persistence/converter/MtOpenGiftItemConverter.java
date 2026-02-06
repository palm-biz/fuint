package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtOpenGiftItem;
import com.fuint.infrastructure.persistence.entity.MtOpenGiftItemDO;
import org.springframework.stereotype.Component;

/**
 * MtOpenGiftItem 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtOpenGiftItemConverter {

    /**
     * DO -> Domain Entity
     */
    public MtOpenGiftItem toDomain(MtOpenGiftItemDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtOpenGiftItem entity = new MtOpenGiftItem();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserId(dataObject.getUserId());
        entity.setOpenGiftId(dataObject.getOpenGiftId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtOpenGiftItemDO toDataObject(MtOpenGiftItem entity) {
        if (entity == null) {
            return null;
        }

        MtOpenGiftItemDO dataObject = new MtOpenGiftItemDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setOpenGiftId(entity.getOpenGiftId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
