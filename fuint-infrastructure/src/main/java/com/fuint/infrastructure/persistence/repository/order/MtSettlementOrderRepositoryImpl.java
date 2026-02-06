package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtSettlementOrder;
import com.fuint.domain.repository.order.MtSettlementOrderRepository;
import com.fuint.infrastructure.persistence.converter.MtSettlementOrderConverter;
import com.fuint.infrastructure.persistence.entity.MtSettlementOrderDO;
import com.fuint.infrastructure.persistence.mapper.MtSettlementOrderMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtSettlementOrderRepositoryImpl implements MtSettlementOrderRepository {
    private final MtSettlementOrderMapper mtSettlementOrderMapper;
    private final MtSettlementOrderConverter mtSettlementOrderConverter;

    public MtSettlementOrderRepositoryImpl(MtSettlementOrderMapper mtSettlementOrderMapper, MtSettlementOrderConverter mtSettlementOrderConverter) {
        this.mtSettlementOrderMapper = mtSettlementOrderMapper;
        this.mtSettlementOrderConverter = mtSettlementOrderConverter;
    }

    @Override
    public MtSettlementOrder save(MtSettlementOrder mtSettlementOrder) {
        MtSettlementOrderDO mtSettlementOrderDO = mtSettlementOrderConverter.toDataObject(mtSettlementOrder);
        if (mtSettlementOrderDO.getId() == null) {
            mtSettlementOrderMapper.insert(mtSettlementOrderDO);
        } else {
            mtSettlementOrderMapper.updateById(mtSettlementOrderDO);
        }
        return mtSettlementOrderConverter.toDomain(mtSettlementOrderDO);
    }

    @Override
    public Optional<MtSettlementOrder> findById(Long id) {
        MtSettlementOrderDO mtSettlementOrderDO = mtSettlementOrderMapper.selectById(id);
        return Optional.ofNullable(mtSettlementOrderConverter.toDomain(mtSettlementOrderDO));
    }

    @Override
    public List<MtSettlementOrder> findAll() {
        List<MtSettlementOrderDO> mtSettlementOrderDOS = mtSettlementOrderMapper.selectList(null);
        return mtSettlementOrderDOS.stream().map(mtSettlementOrderConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtSettlementOrderMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtSettlementOrderMapper.selectCount(new LambdaQueryWrapper<MtSettlementOrderDO>().eq(MtSettlementOrderDO::getId, id)) > 0;
    }

    @Override
    public List<MtSettlementOrder> findBySettlementId(Long settlementId) {
        List<MtSettlementOrderDO> mtSettlementOrderDOS = mtSettlementOrderMapper.selectList(new LambdaQueryWrapper<MtSettlementOrderDO>().eq(MtSettlementOrderDO::getSettlementId, settlementId));
        return mtSettlementOrderDOS.stream().map(mtSettlementOrderConverter::toDomain).collect(Collectors.toList());
    }
}
