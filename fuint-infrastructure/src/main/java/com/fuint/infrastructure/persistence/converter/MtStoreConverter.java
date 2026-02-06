package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.store.MtStore;
import com.fuint.infrastructure.persistence.entity.MtStoreDO;
import org.springframework.stereotype.Component;

/**
 * MtStore 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtStoreConverter {

    /**
     * DO -> Domain Entity
     */
    public MtStore toDomain(MtStoreDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtStore entity = new MtStore();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setName(dataObject.getName());
        entity.setLogo(dataObject.getLogo());
        entity.setQrCode(dataObject.getQrCode());
        entity.setIsDefault(dataObject.getIsDefault());
        entity.setContact(dataObject.getContact());
        entity.setWxMchId(dataObject.getWxMchId());
        entity.setWxApiV2(dataObject.getWxApiV2());
        entity.setWxCertPath(dataObject.getWxCertPath());
        entity.setAlipayAppId(dataObject.getAlipayAppId());
        entity.setAlipayPrivateKey(dataObject.getAlipayPrivateKey());
        entity.setAlipayPublicKey(dataObject.getAlipayPublicKey());
        entity.setPhone(dataObject.getPhone());
        entity.setAddress(dataObject.getAddress());
        entity.setLatitude(dataObject.getLatitude());
        entity.setLongitude(dataObject.getLongitude());
        entity.setDistance(dataObject.getDistance());
        entity.setHours(dataObject.getHours());
        entity.setLicense(dataObject.getLicense());
        entity.setCreditCode(dataObject.getCreditCode());
        entity.setBankName(dataObject.getBankName());
        entity.setBankCardName(dataObject.getBankCardName());
        entity.setBankCardNo(dataObject.getBankCardNo());
        entity.setDescription(dataObject.getDescription());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtStoreDO toDataObject(MtStore entity) {
        if (entity == null) {
            return null;
        }

        MtStoreDO dataObject = new MtStoreDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setName(entity.getName());
        dataObject.setLogo(entity.getLogo());
        dataObject.setQrCode(entity.getQrCode());
        dataObject.setIsDefault(entity.getIsDefault());
        dataObject.setContact(entity.getContact());
        dataObject.setWxMchId(entity.getWxMchId());
        dataObject.setWxApiV2(entity.getWxApiV2());
        dataObject.setWxCertPath(entity.getWxCertPath());
        dataObject.setAlipayAppId(entity.getAlipayAppId());
        dataObject.setAlipayPrivateKey(entity.getAlipayPrivateKey());
        dataObject.setAlipayPublicKey(entity.getAlipayPublicKey());
        dataObject.setPhone(entity.getPhone());
        dataObject.setAddress(entity.getAddress());
        dataObject.setLatitude(entity.getLatitude());
        dataObject.setLongitude(entity.getLongitude());
        dataObject.setDistance(entity.getDistance());
        dataObject.setHours(entity.getHours());
        dataObject.setLicense(entity.getLicense());
        dataObject.setCreditCode(entity.getCreditCode());
        dataObject.setBankName(entity.getBankName());
        dataObject.setBankCardName(entity.getBankCardName());
        dataObject.setBankCardNo(entity.getBankCardNo());
        dataObject.setDescription(entity.getDescription());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
