package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtCoupon;
import com.fuint.infrastructure.persistence.entity.MtCouponDO;
import org.springframework.stereotype.Component;

/**
 * MtCoupon 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCouponConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCoupon toDomain(MtCouponDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCoupon entity = new MtCoupon();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setGroupId(dataObject.getGroupId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setType(dataObject.getType());
        entity.setContent(dataObject.getContent());
        entity.setName(dataObject.getName());
        entity.setIsGive(dataObject.getIsGive());
        entity.setPoint(dataObject.getPoint());
        entity.setApplyGoods(dataObject.getApplyGoods());
        entity.setReceiveCode(dataObject.getReceiveCode());
        entity.setUseFor(dataObject.getUseFor());
        entity.setExpireType(dataObject.getExpireType());
        entity.setExpireTime(dataObject.getExpireTime());
        entity.setBeginTime(dataObject.getBeginTime());
        entity.setEndTime(dataObject.getEndTime());
        entity.setAmount(dataObject.getAmount());
        entity.setSendWay(dataObject.getSendWay());
        entity.setSendNum(dataObject.getSendNum());
        entity.setTotal(dataObject.getTotal());
        entity.setLimitNum(dataObject.getLimitNum());
        entity.setExceptTime(dataObject.getExceptTime());
        entity.setStoreIds(dataObject.getStoreIds());
        entity.setGradeIds(dataObject.getGradeIds());
        entity.setDescription(dataObject.getDescription());
        entity.setImage(dataObject.getImage());
        entity.setRemarks(dataObject.getRemarks());
        entity.setInRule(dataObject.getInRule());
        entity.setOutRule(dataObject.getOutRule());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCouponDO toDataObject(MtCoupon entity) {
        if (entity == null) {
            return null;
        }

        MtCouponDO dataObject = new MtCouponDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setGroupId(entity.getGroupId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setType(entity.getType());
        dataObject.setContent(entity.getContent());
        dataObject.setName(entity.getName());
        dataObject.setIsGive(entity.getIsGive());
        dataObject.setPoint(entity.getPoint());
        dataObject.setApplyGoods(entity.getApplyGoods());
        dataObject.setReceiveCode(entity.getReceiveCode());
        dataObject.setUseFor(entity.getUseFor());
        dataObject.setExpireType(entity.getExpireType());
        dataObject.setExpireTime(entity.getExpireTime());
        dataObject.setBeginTime(entity.getBeginTime());
        dataObject.setEndTime(entity.getEndTime());
        dataObject.setAmount(entity.getAmount());
        dataObject.setSendWay(entity.getSendWay());
        dataObject.setSendNum(entity.getSendNum());
        dataObject.setTotal(entity.getTotal());
        dataObject.setLimitNum(entity.getLimitNum());
        dataObject.setExceptTime(entity.getExceptTime());
        dataObject.setStoreIds(entity.getStoreIds());
        dataObject.setGradeIds(entity.getGradeIds());
        dataObject.setDescription(entity.getDescription());
        dataObject.setImage(entity.getImage());
        dataObject.setRemarks(entity.getRemarks());
        dataObject.setInRule(entity.getInRule());
        dataObject.setOutRule(entity.getOutRule());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
