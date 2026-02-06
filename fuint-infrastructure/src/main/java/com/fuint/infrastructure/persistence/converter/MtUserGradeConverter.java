package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.user.MtUserGrade;
import com.fuint.infrastructure.persistence.entity.MtUserGradeDO;
import org.springframework.stereotype.Component;

/**
 * MtUserGrade 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtUserGradeConverter {

    /**
     * DO -> Domain Entity
     */
    public MtUserGrade toDomain(MtUserGradeDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtUserGrade entity = new MtUserGrade();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setMerchantId(dataObject.getMerchantId());
        entity.setGrade(dataObject.getGrade());
        entity.setName(dataObject.getName());
        entity.setCatchCondition(dataObject.getCatchCondition());
        entity.setCatchType(dataObject.getCatchType());
        entity.setCatchValue(dataObject.getCatchValue());
        entity.setUserPrivilege(dataObject.getUserPrivilege());
        entity.setValidDay(dataObject.getValidDay());
        entity.setDiscount(dataObject.getDiscount());
        entity.setSpeedPoint(dataObject.getSpeedPoint());
        entity.setRebate(dataObject.getRebate());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtUserGradeDO toDataObject(MtUserGrade entity) {
        if (entity == null) {
            return null;
        }

        MtUserGradeDO dataObject = new MtUserGradeDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setMerchantId(entity.getMerchantId());
        dataObject.setGrade(entity.getGrade());
        dataObject.setName(entity.getName());
        dataObject.setCatchCondition(entity.getCatchCondition());
        dataObject.setCatchType(entity.getCatchType());
        dataObject.setCatchValue(entity.getCatchValue());
        dataObject.setUserPrivilege(entity.getUserPrivilege());
        dataObject.setValidDay(entity.getValidDay());
        dataObject.setDiscount(entity.getDiscount());
        dataObject.setSpeedPoint(entity.getSpeedPoint());
        dataObject.setRebate(entity.getRebate());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
