package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtOrderAddress;
import com.fuint.infrastructure.persistence.entity.MtOrderAddressDO;
import org.springframework.stereotype.Component;

/**
 * MtOrderAddress 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtOrderAddressConverter {

    /**
     * DO -> Domain Entity
     */
    public MtOrderAddress toDomain(MtOrderAddressDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtOrderAddress entity = new MtOrderAddress();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setName(dataObject.getName());
        entity.setMobile(dataObject.getMobile());
        entity.setProvinceId(dataObject.getProvinceId());
        entity.setCityId(dataObject.getCityId());
        entity.setRegionId(dataObject.getRegionId());
        entity.setDetail(dataObject.getDetail());
        entity.setOrderId(dataObject.getOrderId());
        entity.setUserId(dataObject.getUserId());
        entity.setCreateTime(dataObject.getCreateTime());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtOrderAddressDO toDataObject(MtOrderAddress entity) {
        if (entity == null) {
            return null;
        }

        MtOrderAddressDO dataObject = new MtOrderAddressDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setName(entity.getName());
        dataObject.setMobile(entity.getMobile());
        dataObject.setProvinceId(entity.getProvinceId());
        dataObject.setCityId(entity.getCityId());
        dataObject.setRegionId(entity.getRegionId());
        dataObject.setDetail(entity.getDetail());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setCreateTime(entity.getCreateTime());

        return dataObject;
    }
}
