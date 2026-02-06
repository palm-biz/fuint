package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtUser;
import com.fuint.infrastructure.persistence.entity.MtUserDO;
import org.springframework.stereotype.Component;

/**
 * MtUser 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUserConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUser toDomain(MtUserDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUser entity = new MtUser();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setUserNo(dataObject.getUserNo());
        entity.setAvatar(dataObject.getAvatar());
        entity.setGroupId(dataObject.getGroupId());
        entity.setName(dataObject.getName());
        entity.setOpenId(dataObject.getOpenId());
        entity.setMobile(dataObject.getMobile());
        entity.setIdcard(dataObject.getIdcard());
        entity.setGradeId(dataObject.getGradeId());
        entity.setStartTime(dataObject.getStartTime());
        entity.setEndTime(dataObject.getEndTime());
        entity.setBalance(dataObject.getBalance());
        entity.setPoint(dataObject.getPoint());
        entity.setSex(dataObject.getSex());
        entity.setBirthday(dataObject.getBirthday());
        entity.setCarNo(dataObject.getCarNo());
        entity.setSource(dataObject.getSource());
        entity.setPassword(dataObject.getPassword());
        entity.setSalt(dataObject.getSalt());
        entity.setAddress(dataObject.getAddress());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setStoreId(dataObject.getStoreId());
        entity.setIsStaff(dataObject.getIsStaff());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setStatus(dataObject.getStatus());
        entity.setDescription(dataObject.getDescription());
        entity.setIp(dataObject.getIp());
        entity.setOperator(dataObject.getOperator());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUserDO toDataObject(MtUser entity) {
        if (entity == null) {
            return null;
        }

        MtUserDO dataObject = new MtUserDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setUserNo(entity.getUserNo());
        dataObject.setAvatar(entity.getAvatar());
        dataObject.setGroupId(entity.getGroupId());
        dataObject.setName(entity.getName());
        dataObject.setOpenId(entity.getOpenId());
        dataObject.setMobile(entity.getMobile());
        dataObject.setIdcard(entity.getIdcard());
        dataObject.setGradeId(entity.getGradeId());
        dataObject.setStartTime(entity.getStartTime());
        dataObject.setEndTime(entity.getEndTime());
        dataObject.setBalance(entity.getBalance());
        dataObject.setPoint(entity.getPoint());
        dataObject.setSex(entity.getSex());
        dataObject.setBirthday(entity.getBirthday());
        dataObject.setCarNo(entity.getCarNo());
        dataObject.setSource(entity.getSource());
        dataObject.setPassword(entity.getPassword());
        dataObject.setSalt(entity.getSalt());
        dataObject.setAddress(entity.getAddress());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setStoreId(entity.getStoreId());
        dataObject.setIsStaff(entity.getIsStaff());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setStatus(entity.getStatus());
        dataObject.setDescription(entity.getDescription());
        dataObject.setIp(entity.getIp());
        dataObject.setOperator(entity.getOperator());

        return dataObject;
    }
}
