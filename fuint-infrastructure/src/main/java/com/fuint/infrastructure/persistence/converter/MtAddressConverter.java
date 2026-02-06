package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtAddress;
import com.fuint.infrastructure.persistence.entity.MtAddressDO;
import org.springframework.stereotype.Component;

/**
 * MtAddress 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtAddressConverter {

    /**
     * DO -> Domain Entity
     */
    public MtAddress toDomain(MtAddressDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtAddress entity = new MtAddress();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserId(dataObject.getUserId());
        entity.setName(dataObject.getName());
        entity.setMobile(dataObject.getMobile());
        entity.setProvinceId(dataObject.getProvinceId());
        entity.setCityId(dataObject.getCityId());
        entity.setRegionId(dataObject.getRegionId());
        entity.setDetail(dataObject.getDetail());
        entity.setIsDefault(dataObject.getIsDefault());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtAddressDO toDataObject(MtAddress entity) {
        if (entity == null) {
            return null;
        }

        MtAddressDO dataObject = new MtAddressDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setName(entity.getName());
        dataObject.setMobile(entity.getMobile());
        dataObject.setProvinceId(entity.getProvinceId());
        dataObject.setCityId(entity.getCityId());
        dataObject.setRegionId(entity.getRegionId());
        dataObject.setDetail(entity.getDetail());
        dataObject.setIsDefault(entity.getIsDefault());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
