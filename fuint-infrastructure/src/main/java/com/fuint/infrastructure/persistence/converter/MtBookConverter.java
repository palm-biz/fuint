package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.booking.MtBook;
import com.fuint.infrastructure.persistence.entity.MtBookDO;
import org.springframework.stereotype.Component;

/**
 * MtBook 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtBookConverter {

    /**
     * DO -> Domain Entity
     */
    public MtBook toDomain(MtBookDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtBook entity = new MtBook();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setName(dataObject.getName());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setType(dataObject.getType());
        entity.setLogo(dataObject.getLogo());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setCateId(dataObject.getCateId());
        entity.setServiceDates(dataObject.getServiceDates());
        entity.setServiceTimes(dataObject.getServiceTimes());
        entity.setServiceStaffIds(dataObject.getServiceStaffIds());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setSort(dataObject.getSort());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtBookDO toDataObject(MtBook entity) {
        if (entity == null) {
            return null;
        }

        MtBookDO dataObject = new MtBookDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setName(entity.getName());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setType(entity.getType());
        dataObject.setLogo(entity.getLogo());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setCateId(entity.getCateId());
        dataObject.setServiceDates(entity.getServiceDates());
        dataObject.setServiceTimes(entity.getServiceTimes());
        dataObject.setServiceStaffIds(entity.getServiceStaffIds());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setSort(entity.getSort());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
