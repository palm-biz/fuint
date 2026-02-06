package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.common.MtRegion;
import com.fuint.infrastructure.persistence.entity.MtRegionDO;
import org.springframework.stereotype.Component;

/**
 * MtRegion 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtRegionConverter {

    /**
     * DO -> Domain Entity
     */
    public MtRegion toDomain(MtRegionDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtRegion entity = new MtRegion();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setName(dataObject.getName());
        entity.setPid(dataObject.getPid());
        entity.setCode(dataObject.getCode());
        entity.setLevel(dataObject.getLevel());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtRegionDO toDataObject(MtRegion entity) {
        if (entity == null) {
            return null;
        }

        MtRegionDO dataObject = new MtRegionDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setName(entity.getName());
        dataObject.setPid(entity.getPid());
        dataObject.setCode(entity.getCode());
        dataObject.setLevel(entity.getLevel());

        return dataObject;
    }
}
