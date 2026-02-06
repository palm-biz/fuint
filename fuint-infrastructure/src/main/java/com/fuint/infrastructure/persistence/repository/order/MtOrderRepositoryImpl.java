package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtOrder;
import com.fuint.domain.repository.order.MtOrderRepository;
import com.fuint.infrastructure.persistence.converter.MtOrderConverter;
import com.fuint.infrastructure.persistence.entity.MtOrderDO;
import com.fuint.infrastructure.persistence.mapper.MtOrderMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtOrderRepositoryImpl implements MtOrderRepository {
    private final MtOrderMapper mtOrderMapper;
    private final MtOrderConverter mtOrderConverter;

    public MtOrderRepositoryImpl(MtOrderMapper mtOrderMapper, MtOrderConverter mtOrderConverter) {
        this.mtOrderMapper = mtOrderMapper;
        this.mtOrderConverter = mtOrderConverter;
    }

    @Override
    public MtOrder save(MtOrder mtOrder) {
        MtOrderDO mtOrderDO = mtOrderConverter.toDataObject(mtOrder);
        if (mtOrderDO.getId() == null) {
            mtOrderMapper.insert(mtOrderDO);
        } else {
            mtOrderMapper.updateById(mtOrderDO);
        }
        return mtOrderConverter.toDomain(mtOrderDO);
    }

    @Override
    public Optional<MtOrder> findById(Long id) {
        MtOrderDO mtOrderDO = mtOrderMapper.selectById(id);
        return Optional.ofNullable(mtOrderConverter.toDomain(mtOrderDO));
    }

    @Override
    public List<MtOrder> findAll() {
        List<MtOrderDO> mtOrderDOS = mtOrderMapper.selectList(null);
        return mtOrderDOS.stream().map(mtOrderConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtOrderMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtOrderMapper.selectCount(new LambdaQueryWrapper<MtOrderDO>().eq(MtOrderDO::getId, id)) > 0;
    }

    @Override
    public List<MtOrder> findByUserId(Long userId) {
        List<MtOrderDO> mtOrderDOS = mtOrderMapper.selectList(new LambdaQueryWrapper<MtOrderDO>().eq(MtOrderDO::getUserId, userId));
        return mtOrderDOS.stream().map(mtOrderConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<MtOrder> findByOrderSn(String orderSn) {
        MtOrderDO mtOrderDO = mtOrderMapper.selectOne(new LambdaQueryWrapper<MtOrderDO>().eq(MtOrderDO::getOrderSn, orderSn));
        return Optional.ofNullable(mtOrderConverter.toDomain(mtOrderDO));
    }
}
