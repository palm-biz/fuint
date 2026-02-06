package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtSettlement;
import com.fuint.domain.repository.order.MtSettlementRepository;
import com.fuint.infrastructure.persistence.converter.MtSettlementConverter;
import com.fuint.infrastructure.persistence.entity.MtSettlementDO;
import com.fuint.infrastructure.persistence.mapper.MtSettlementMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtSettlementRepositoryImpl implements MtSettlementRepository {
    private final MtSettlementMapper mtSettlementMapper;
    private final MtSettlementConverter mtSettlementConverter;

    public MtSettlementRepositoryImpl(MtSettlementMapper mtSettlementMapper, MtSettlementConverter mtSettlementConverter) {
        this.mtSettlementMapper = mtSettlementMapper;
        this.mtSettlementConverter = mtSettlementConverter;
    }

    @Override
    public MtSettlement save(MtSettlement mtSettlement) {
        MtSettlementDO mtSettlementDO = mtSettlementConverter.toDataObject(mtSettlement);
        if (mtSettlementDO.getId() == null) {
            mtSettlementMapper.insert(mtSettlementDO);
        } else {
            mtSettlementMapper.updateById(mtSettlementDO);
        }
        return mtSettlementConverter.toDomain(mtSettlementDO);
    }

    @Override
    public Optional<MtSettlement> findById(Long id) {
        MtSettlementDO mtSettlementDO = mtSettlementMapper.selectById(id);
        return Optional.ofNullable(mtSettlementConverter.toDomain(mtSettlementDO));
    }

    @Override
    public List<MtSettlement> findAll() {
        List<MtSettlementDO> mtSettlementDOS = mtSettlementMapper.selectList(null);
        return mtSettlementDOS.stream().map(mtSettlementConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtSettlementMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtSettlementMapper.selectCount(new LambdaQueryWrapper<MtSettlementDO>().eq(MtSettlementDO::getId, id)) > 0;
    }

    @Override
    public List<MtSettlement> findByStatus(String status) {
        List<MtSettlementDO> mtSettlementDOS = mtSettlementMapper.selectList(new LambdaQueryWrapper<MtSettlementDO>().eq(MtSettlementDO::getStatus, status));
        return mtSettlementDOS.stream().map(mtSettlementConverter::toDomain).collect(Collectors.toList());
    }
}
