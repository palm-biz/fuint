package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtConfirmLog;
import com.fuint.domain.repository.order.MtConfirmLogRepository;
import com.fuint.infrastructure.persistence.converter.MtConfirmLogConverter;
import com.fuint.infrastructure.persistence.entity.MtConfirmLogDO;
import com.fuint.infrastructure.persistence.mapper.MtConfirmLogMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtConfirmLogRepositoryImpl implements MtConfirmLogRepository {
    private final MtConfirmLogMapper mtConfirmLogMapper;
    private final MtConfirmLogConverter mtConfirmLogConverter;

    public MtConfirmLogRepositoryImpl(MtConfirmLogMapper mtConfirmLogMapper, MtConfirmLogConverter mtConfirmLogConverter) {
        this.mtConfirmLogMapper = mtConfirmLogMapper;
        this.mtConfirmLogConverter = mtConfirmLogConverter;
    }

    @Override
    public MtConfirmLog save(MtConfirmLog mtConfirmLog) {
        MtConfirmLogDO mtConfirmLogDO = mtConfirmLogConverter.toDataObject(mtConfirmLog);
        if (mtConfirmLogDO.getId() == null) {
            mtConfirmLogMapper.insert(mtConfirmLogDO);
        } else {
            mtConfirmLogMapper.updateById(mtConfirmLogDO);
        }
        return mtConfirmLogConverter.toDomain(mtConfirmLogDO);
    }

    @Override
    public Optional<MtConfirmLog> findById(Long id) {
        MtConfirmLogDO mtConfirmLogDO = mtConfirmLogMapper.selectById(id);
        return Optional.ofNullable(mtConfirmLogConverter.toDomain(mtConfirmLogDO));
    }

    @Override
    public List<MtConfirmLog> findAll() {
        List<MtConfirmLogDO> mtConfirmLogDOS = mtConfirmLogMapper.selectList(null);
        return mtConfirmLogDOS.stream().map(mtConfirmLogConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtConfirmLogMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtConfirmLogMapper.selectCount(new LambdaQueryWrapper<MtConfirmLogDO>().eq(MtConfirmLogDO::getId, id)) > 0;
    }

    @Override
    public List<MtConfirmLog> findByUserCouponId(Long userCouponId) {
        List<MtConfirmLogDO> mtConfirmLogDOS = mtConfirmLogMapper.selectList(new LambdaQueryWrapper<MtConfirmLogDO>().eq(MtConfirmLogDO::getUserCouponId, userCouponId));
        return mtConfirmLogDOS.stream().map(mtConfirmLogConverter::toDomain).collect(Collectors.toList());
    }
}
