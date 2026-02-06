package com.fuint.infrastructure.persistence.converter;

import com.fuint.domain.model.order.MtSettlementOrder;
import com.fuint.infrastructure.persistence.entity.MtSettlementOrderDO;
import org.springframework.stereotype.Component;

/**
 * MtSettlementOrder 转换器
 * DO (数据对象) ↔ Entity (领域实体)
 *
 * @author fuint
 * @since 2.0.0
 */
@Component
public class MtSettlementOrderConverter {

    /**
     * DO -> Domain Entity
     */
    public MtSettlementOrder toDomain(MtSettlementOrderDO dataObject) {
        if (dataObject == null) {
            return null;
        }

        MtSettlementOrder entity = new MtSettlementOrder();
        entity.setId(dataObject.getId());
        entity.setTenantId(dataObject.getTenantId());
        entity.setSettlementId(dataObject.getSettlementId());
        entity.setOrderId(dataObject.getOrderId());
        entity.setCreateTime(dataObject.getCreateTime());
        entity.setUpdateTime(dataObject.getUpdateTime());
        entity.setDescription(dataObject.getDescription());
        entity.setOperator(dataObject.getOperator());
        entity.setStatus(dataObject.getStatus());

        return entity;
    }

    /**
     * Domain Entity -> DO
     */
    public MtSettlementOrderDO toDataObject(MtSettlementOrder entity) {
        if (entity == null) {
            return null;
        }

        MtSettlementOrderDO dataObject = new MtSettlementOrderDO();
        dataObject.setId(entity.getId());
        dataObject.setTenantId(entity.getTenantId());
        dataObject.setSettlementId(entity.getSettlementId());
        dataObject.setOrderId(entity.getOrderId());
        dataObject.setCreateTime(entity.getCreateTime());
        dataObject.setUpdateTime(entity.getUpdateTime());
        dataObject.setDescription(entity.getDescription());
        dataObject.setOperator(entity.getOperator());
        dataObject.setStatus(entity.getStatus());

        return dataObject;
    }
}
