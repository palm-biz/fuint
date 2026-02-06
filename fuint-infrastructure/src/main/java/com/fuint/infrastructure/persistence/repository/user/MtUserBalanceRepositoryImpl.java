package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtUserBalance;
import com.fuint.domain.repository.user.MtUserBalanceRepository;
import com.fuint.infrastructure.persistence.converter.MtUserBalanceConverter;
import com.fuint.infrastructure.persistence.entity.MtUserBalanceDO;
import com.fuint.infrastructure.persistence.mapper.MtUserBalanceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员余额仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtUserBalanceRepositoryImpl implements MtUserBalanceRepository {

    private final MtUserBalanceMapper mtUserBalanceMapper;
    private final MtUserBalanceConverter mtUserBalanceConverter;

    public MtUserBalanceRepositoryImpl(MtUserBalanceMapper mtUserBalanceMapper, MtUserBalanceConverter mtUserBalanceConverter) {
        this.mtUserBalanceMapper = mtUserBalanceMapper;
        this.mtUserBalanceConverter = mtUserBalanceConverter;
    }

    @Override
    public MtUserBalance save(MtUserBalance mtUserBalance) {
        MtUserBalanceDO mtUserBalanceDO = mtUserBalanceConverter.toDataObject(mtUserBalance);
        if (mtUserBalanceDO.getId() == null) {
            mtUserBalanceMapper.insert(mtUserBalanceDO);
        } else {
            mtUserBalanceMapper.updateById(mtUserBalanceDO);
        }
        return mtUserBalanceConverter.toDomain(mtUserBalanceDO);
    }

    @Override
    public Optional<MtUserBalance> findById(Long id) {
        MtUserBalanceDO mtUserBalanceDO = mtUserBalanceMapper.selectById(id);
        return Optional.ofNullable(mtUserBalanceConverter.toDomain(mtUserBalanceDO));
    }

    @Override
    public List<MtUserBalance> findAll() {
        List<MtUserBalanceDO> mtUserBalanceDOS = mtUserBalanceMapper.selectList(null);
        return mtUserBalanceDOS.stream()
                .map(mtUserBalanceConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtUserBalanceMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtUserBalanceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserBalanceDO::getId, id);
        return mtUserBalanceMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Optional<MtUserBalance> findByUserId(Long userId) {
        LambdaQueryWrapper<MtUserBalanceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserBalanceDO::getUserId, userId);
        MtUserBalanceDO mtUserBalanceDO = mtUserBalanceMapper.selectOne(wrapper);
        return Optional.ofNullable(mtUserBalanceConverter.toDomain(mtUserBalanceDO));
    }
}
