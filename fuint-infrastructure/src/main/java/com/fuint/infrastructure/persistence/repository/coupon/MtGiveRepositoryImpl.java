package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtGive;
import com.fuint.domain.repository.coupon.MtGiveRepository;
import com.fuint.infrastructure.persistence.converter.MtGiveConverter;
import com.fuint.infrastructure.persistence.entity.MtGiveDO;
import com.fuint.infrastructure.persistence.mapper.MtGiveMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 转赠记录仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtGiveRepositoryImpl implements MtGiveRepository {

    private final MtGiveMapper mtGiveMapper;
    private final MtGiveConverter mtGiveConverter;

    public MtGiveRepositoryImpl(MtGiveMapper mtGiveMapper, MtGiveConverter mtGiveConverter) {
        this.mtGiveMapper = mtGiveMapper;
        this.mtGiveConverter = mtGiveConverter;
    }

    @Override
    public MtGive save(MtGive mtGive) {
        MtGiveDO mtGiveDO = mtGiveConverter.toDataObject(mtGive);
        if (mtGiveDO.getId() == null) {
            mtGiveMapper.insert(mtGiveDO);
        } else {
            mtGiveMapper.updateById(mtGiveDO);
        }
        return mtGiveConverter.toDomain(mtGiveDO);
    }

    @Override
    public Optional<MtGive> findById(Long id) {
        MtGiveDO mtGiveDO = mtGiveMapper.selectById(id);
        return Optional.ofNullable(mtGiveConverter.toDomain(mtGiveDO));
    }

    @Override
    public List<MtGive> findAll() {
        List<MtGiveDO> mtGiveDOS = mtGiveMapper.selectList(null);
        return mtGiveDOS.stream()
                .map(mtGiveConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtGiveMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtGiveDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtGiveDO::getId, id);
        return mtGiveMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtGive> findByUserId(Long userId) {
        LambdaQueryWrapper<MtGiveDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtGiveDO::getUserId, userId);
        List<MtGiveDO> mtGiveDOS = mtGiveMapper.selectList(wrapper);
        return mtGiveDOS.stream()
                .map(mtGiveConverter::toDomain)
                .collect(Collectors.toList());
    }
}
