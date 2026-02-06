package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.booking.MtBookItem;
import com.fuint.infrastructure.persistence.entity.MtBookItemDO;
import org.springframework.stereotype.Component;

/**
 * MtBookItem 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtBookItemConverter {

    /**
     * DO -> Domain Entity
     */
    public MtBookItem toDomain(MtBookItemDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtBookItem entity = new MtBookItem();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setCateId(dataObject.getCateId());
        entity.setBookId(dataObject.getBookId());
        entity.setUserId(dataObject.getUserId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setVerifyCode(dataObject.getVerifyCode());
        entity.setContact(dataObject.getContact());
        entity.setMobile(dataObject.getMobile());
        entity.setServiceDate(dataObject.getServiceDate());
        entity.setServiceTime(dataObject.getServiceTime());
        entity.setRemark(dataObject.getRemark());
        entity.setServiceStaffId(dataObject.getServiceStaffId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtBookItemDO toDataObject(MtBookItem entity) {
        if (entity == null) {
            return null;
        }

        MtBookItemDO dataObject = new MtBookItemDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setCateId(entity.getCateId());
        dataObject.setBookId(entity.getBookId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setVerifyCode(entity.getVerifyCode());
        dataObject.setContact(entity.getContact());
        dataObject.setMobile(entity.getMobile());
        dataObject.setServiceDate(entity.getServiceDate());
        dataObject.setServiceTime(entity.getServiceTime());
        dataObject.setRemark(entity.getRemark());
        dataObject.setServiceStaffId(entity.getServiceStaffId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
