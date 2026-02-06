package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.goods.MtGoods;
import com.fuint.infrastructure.persistence.entity.MtGoodsDO;
import org.springframework.stereotype.Component;

/**
 * MtGoods 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtGoodsConverter {

    /**
     * DO -> Domain Entity
     */
    public MtGoods toDomain(MtGoodsDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtGoods entity = new MtGoods();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setName(dataObject.getName());
        entity.setType(dataObject.getType());
        entity.setCateId(dataObject.getCateId());
        entity.setBookId(dataObject.getBookId());
        entity.setGoodsNo(dataObject.getGoodsNo());
        entity.setPlatform(dataObject.getPlatform());
        entity.setIsSingleSpec(dataObject.getIsSingleSpec());
        entity.setLogo(dataObject.getLogo());
        entity.setImages(dataObject.getImages());
        entity.setPrice(dataObject.getPrice());
        entity.setLinePrice(dataObject.getLinePrice());
        entity.setCostPrice(dataObject.getCostPrice());
        entity.setStock(dataObject.getStock());
        entity.setCouponIds(dataObject.getCouponIds());
        entity.setServiceTime(dataObject.getServiceTime());
        entity.setWeight(dataObject.getWeight());
        entity.setInitSale(dataObject.getInitSale());
        entity.setSalePoint(dataObject.getSalePoint());
        entity.setCanUsePoint(dataObject.getCanUsePoint());
        entity.setIsMemberDiscount(dataObject.getIsMemberDiscount());
        entity.setSort(dataObject.getSort());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtGoodsDO toDataObject(MtGoods entity) {
        if (entity == null) {
            return null;
        }

        MtGoodsDO dataObject = new MtGoodsDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setName(entity.getName());
        dataObject.setType(entity.getType());
        dataObject.setCateId(entity.getCateId());
        dataObject.setBookId(entity.getBookId());
        dataObject.setGoodsNo(entity.getGoodsNo());
        dataObject.setPlatform(entity.getPlatform());
        dataObject.setIsSingleSpec(entity.getIsSingleSpec());
        dataObject.setLogo(entity.getLogo());
        dataObject.setImages(entity.getImages());
        dataObject.setPrice(entity.getPrice());
        dataObject.setLinePrice(entity.getLinePrice());
        dataObject.setCostPrice(entity.getCostPrice());
        dataObject.setStock(entity.getStock());
        dataObject.setCouponIds(entity.getCouponIds());
        dataObject.setServiceTime(entity.getServiceTime());
        dataObject.setWeight(entity.getWeight());
        dataObject.setInitSale(entity.getInitSale());
        dataObject.setSalePoint(entity.getSalePoint());
        dataObject.setCanUsePoint(entity.getCanUsePoint());
        dataObject.setIsMemberDiscount(entity.getIsMemberDiscount());
        dataObject.setSort(entity.getSort());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
