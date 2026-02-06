package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtSendLog;
import com.fuint.infrastructure.persistence.entity.MtSendLogDO;
import org.springframework.stereotype.Component;

/**
 * MtSendLog 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtSendLogConverter {

    /**
     * DO -> Domain Entity
     */
    public MtSendLog toDomain(MtSendLogDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtSendLog entity = new MtSendLog();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setType(dataObject.getType());
        entity.setUserId(dataObject.getUserId());
        entity.setFileName(dataObject.getFileName());
        entity.setFilePath(dataObject.getFilePath());
        entity.setMobile(dataObject.getMobile());
        entity.setGroupId(dataObject.getGroupId());
        entity.setGroupName(dataObject.getGroupName());
        entity.setCouponId(dataObject.getCouponId());
        entity.setSendNum(dataObject.getSendNum());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setUuid(dataObject.getUuid());
        entity.setRemoveSuccessNum(dataObject.getRemoveSuccessNum());
        entity.setRemoveFailNum(dataObject.getRemoveFailNum());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtSendLogDO toDataObject(MtSendLog entity) {
        if (entity == null) {
            return null;
        }

        MtSendLogDO dataObject = new MtSendLogDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setType(entity.getType());
        dataObject.setUserId(entity.getUserId());
        dataObject.setFileName(entity.getFileName());
        dataObject.setFilePath(entity.getFilePath());
        dataObject.setMobile(entity.getMobile());
        dataObject.setGroupId(entity.getGroupId());
        dataObject.setGroupName(entity.getGroupName());
        dataObject.setCouponId(entity.getCouponId());
        dataObject.setSendNum(entity.getSendNum());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setUuid(entity.getUuid());
        dataObject.setRemoveSuccessNum(entity.getRemoveSuccessNum());
        dataObject.setRemoveFailNum(entity.getRemoveFailNum());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
