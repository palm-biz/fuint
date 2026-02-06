package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtPoint;
import com.fuint.domain.repository.user.MtPointRepository;
import com.fuint.infrastructure.persistence.converter.MtPointConverter;
import com.fuint.infrastructure.persistence.entity.MtPointDO;
import com.fuint.infrastructure.persistence.mapper.MtPointMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员积分明细仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtPointRepositoryImpl implements MtPointRepository {

    private final MtPointMapper mtPointMapper;
    private final MtPointConverter mtPointConverter;

    public MtPointRepositoryImpl(MtPointMapper mtPointMapper, MtPointConverter mtPointConverter) {
        this.mtPointMapper = mtPointMapper;
        this.mtPointConverter = mtPointConverter;
    }

    @Override
    public MtPoint save(MtPoint mtPoint) {
        MtPointDO mtPointDO = mtPointConverter.toDataObject(mtPoint);
        if (mtPointDO.getId() == null) {
            mtPointMapper.insert(mtPointDO);
        } else {
            mtPointMapper.updateById(mtPointDO);
        }
        return mtPointConverter.toDomain(mtPointDO);
    }

    @Override
    public Optional<MtPoint> findById(Long id) {
        MtPointDO mtPointDO = mtPointMapper.selectById(id);
        return Optional.ofNullable(mtPointConverter.toDomain(mtPointDO));
    }

    @Override
    public List<MtPoint> findAll() {
        List<MtPointDO> mtPointDOS = mtPointMapper.selectList(null);
        return mtPointDOS.stream()
                .map(mtPointConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtPointMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtPointDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtPointDO::getId, id);
        return mtPointMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtPoint> findByUserId(Long userId) {
        LambdaQueryWrapper<MtPointDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtPointDO::getUserId, userId);
        List<MtPointDO> mtPointDOS = mtPointMapper.selectList(wrapper);
        return mtPointDOS.stream()
                .map(mtPointConverter::toDomain)
                .collect(Collectors.toList());
    }
}
