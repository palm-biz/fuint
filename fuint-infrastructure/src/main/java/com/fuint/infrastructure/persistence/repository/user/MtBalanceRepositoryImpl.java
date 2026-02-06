package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtBalance;
import com.fuint.domain.repository.user.MtBalanceRepository;
import com.fuint.infrastructure.persistence.converter.MtBalanceConverter;
import com.fuint.infrastructure.persistence.entity.MtBalanceDO;
import com.fuint.infrastructure.persistence.mapper.MtBalanceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员余额明细仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtBalanceRepositoryImpl implements MtBalanceRepository {

    private final MtBalanceMapper mtBalanceMapper;
    private final MtBalanceConverter mtBalanceConverter;

    public MtBalanceRepositoryImpl(MtBalanceMapper mtBalanceMapper, MtBalanceConverter mtBalanceConverter) {
        this.mtBalanceMapper = mtBalanceMapper;
        this.mtBalanceConverter = mtBalanceConverter;
    }

    @Override
    public MtBalance save(MtBalance mtBalance) {
        MtBalanceDO mtBalanceDO = mtBalanceConverter.toDataObject(mtBalance);
        if (mtBalanceDO.getId() == null) {
            mtBalanceMapper.insert(mtBalanceDO);
        } else {
            mtBalanceMapper.updateById(mtBalanceDO);
        }
        return mtBalanceConverter.toDomain(mtBalanceDO);
    }

    @Override
    public Optional<MtBalance> findById(Long id) {
        MtBalanceDO mtBalanceDO = mtBalanceMapper.selectById(id);
        return Optional.ofNullable(mtBalanceConverter.toDomain(mtBalanceDO));
    }

    @Override
    public List<MtBalance> findAll() {
        List<MtBalanceDO> mtBalanceDOS = mtBalanceMapper.selectList(null);
        return mtBalanceDOS.stream()
                .map(mtBalanceConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtBalanceMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtBalanceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtBalanceDO::getId, id);
        return mtBalanceMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtBalance> findByUserId(Long userId) {
        LambdaQueryWrapper<MtBalanceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtBalanceDO::getUserId, userId);
        List<MtBalanceDO> mtBalanceDOS = mtBalanceMapper.selectList(wrapper);
        return mtBalanceDOS.stream()
                .map(mtBalanceConverter::toDomain)
                .collect(Collectors.toList());
    }
}
