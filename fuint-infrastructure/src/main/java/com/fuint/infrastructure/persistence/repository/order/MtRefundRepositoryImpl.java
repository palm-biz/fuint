package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtRefund;
import com.fuint.domain.repository.order.MtRefundRepository;
import com.fuint.infrastructure.persistence.converter.MtRefundConverter;
import com.fuint.infrastructure.persistence.entity.MtRefundDO;
import com.fuint.infrastructure.persistence.mapper.MtRefundMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtRefundRepositoryImpl implements MtRefundRepository {
    private final MtRefundMapper mtRefundMapper;
    private final MtRefundConverter mtRefundConverter;

    public MtRefundRepositoryImpl(MtRefundMapper mtRefundMapper, MtRefundConverter mtRefundConverter) {
        this.mtRefundMapper = mtRefundMapper;
        this.mtRefundConverter = mtRefundConverter;
    }

    @Override
    public MtRefund save(MtRefund mtRefund) {
        MtRefundDO mtRefundDO = mtRefundConverter.toDataObject(mtRefund);
        if (mtRefundDO.getId() == null) {
            mtRefundMapper.insert(mtRefundDO);
        } else {
            mtRefundMapper.updateById(mtRefundDO);
        }
        return mtRefundConverter.toDomain(mtRefundDO);
    }

    @Override
    public Optional<MtRefund> findById(Long id) {
        MtRefundDO mtRefundDO = mtRefundMapper.selectById(id);
        return Optional.ofNullable(mtRefundConverter.toDomain(mtRefundDO));
    }

    @Override
    public List<MtRefund> findAll() {
        List<MtRefundDO> mtRefundDOS = mtRefundMapper.selectList(null);
        return mtRefundDOS.stream().map(mtRefundConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtRefundMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtRefundMapper.selectCount(new LambdaQueryWrapper<MtRefundDO>().eq(MtRefundDO::getId, id)) > 0;
    }

    @Override
    public List<MtRefund> findByOrderId(Long orderId) {
        List<MtRefundDO> mtRefundDOS = mtRefundMapper.selectList(new LambdaQueryWrapper<MtRefundDO>().eq(MtRefundDO::getOrderId, orderId));
        return mtRefundDOS.stream().map(mtRefundConverter::toDomain).collect(Collectors.toList());
    }
}
