package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.coupon.MtCouponGoods;
import com.fuint.infrastructure.persistence.entity.MtCouponGoodsDO;
import org.springframework.stereotype.Component;

/**
 * MtCouponGoods 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCouponGoodsConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCouponGoods toDomain(MtCouponGoodsDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCouponGoods entity = new MtCouponGoods();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setCouponId(dataObject.getCouponId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCouponGoodsDO toDataObject(MtCouponGoods entity) {
        if (entity == null) {
            return null;
        }

        MtCouponGoodsDO dataObject = new MtCouponGoodsDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setCouponId(entity.getCouponId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
