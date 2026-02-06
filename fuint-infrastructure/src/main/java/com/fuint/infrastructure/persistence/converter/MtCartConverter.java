package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtCart;
import com.fuint.infrastructure.persistence.entity.MtCartDO;
import org.springframework.stereotype.Component;

/**
 * MtCart 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtCartConverter {

    /**
     * DO -> Domain Entity
     */
    public MtCart toDomain(MtCartDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtCart entity = new MtCart();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserId(dataObject.getUserId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setIsVisitor(dataObject.getIsVisitor());
        entity.setHangNo(dataObject.getHangNo());
        entity.setSkuId(dataObject.getSkuId());
        entity.setGoodsId(dataObject.getGoodsId());
        entity.setNum(dataObject.getNum());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtCartDO toDataObject(MtCart entity) {
        if (entity == null) {
            return null;
        }

        MtCartDO dataObject = new MtCartDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserId(entity.getUserId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setIsVisitor(entity.getIsVisitor());
        dataObject.setHangNo(entity.getHangNo());
        dataObject.setSkuId(entity.getSkuId());
        dataObject.setGoodsId(entity.getGoodsId());
        dataObject.setNum(entity.getNum());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
