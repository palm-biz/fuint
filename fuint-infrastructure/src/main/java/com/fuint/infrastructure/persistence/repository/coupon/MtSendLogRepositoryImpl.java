package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtSendLog;
import com.fuint.domain.repository.coupon.MtSendLogRepository;
import com.fuint.infrastructure.persistence.converter.MtSendLogConverter;
import com.fuint.infrastructure.persistence.entity.MtSendLogDO;
import com.fuint.infrastructure.persistence.mapper.MtSendLogMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 卡券发放记录仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtSendLogRepositoryImpl implements MtSendLogRepository {

    private final MtSendLogMapper mtSendLogMapper;
    private final MtSendLogConverter mtSendLogConverter;

    public MtSendLogRepositoryImpl(MtSendLogMapper mtSendLogMapper, MtSendLogConverter mtSendLogConverter) {
        this.mtSendLogMapper = mtSendLogMapper;
        this.mtSendLogConverter = mtSendLogConverter;
    }

    @Override
    public MtSendLog save(MtSendLog mtSendLog) {
        MtSendLogDO mtSendLogDO = mtSendLogConverter.toDataObject(mtSendLog);
        if (mtSendLogDO.getId() == null) {
            mtSendLogMapper.insert(mtSendLogDO);
        } else {
            mtSendLogMapper.updateById(mtSendLogDO);
        }
        return mtSendLogConverter.toDomain(mtSendLogDO);
    }

    @Override
    public Optional<MtSendLog> findById(Long id) {
        MtSendLogDO mtSendLogDO = mtSendLogMapper.selectById(id);
        return Optional.ofNullable(mtSendLogConverter.toDomain(mtSendLogDO));
    }

    @Override
    public List<MtSendLog> findAll() {
        List<MtSendLogDO> mtSendLogDOS = mtSendLogMapper.selectList(null);
        return mtSendLogDOS.stream()
                .map(mtSendLogConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtSendLogMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtSendLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtSendLogDO::getId, id);
        return mtSendLogMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtSendLog> findByUserId(Long userId) {
        LambdaQueryWrapper<MtSendLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtSendLogDO::getUserId, userId);
        List<MtSendLogDO> mtSendLogDOS = mtSendLogMapper.selectList(wrapper);
        return mtSendLogDOS.stream()
                .map(mtSendLogConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MtSendLog> findByCouponId(Long couponId) {
        LambdaQueryWrapper<MtSendLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtSendLogDO::getCouponId, couponId);
        List<MtSendLogDO> mtSendLogDOS = mtSendLogMapper.selectList(wrapper);
        return mtSendLogDOS.stream()
                .map(mtSendLogConverter::toDomain)
                .collect(Collectors.toList());
    }
}
