package com.fuint.infrastructure.persistence.repository.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.coupon.MtOpenGift;
import com.fuint.domain.repository.coupon.MtOpenGiftRepository;
import com.fuint.infrastructure.persistence.converter.MtOpenGiftConverter;
import com.fuint.infrastructure.persistence.entity.MtOpenGiftDO;
import com.fuint.infrastructure.persistence.mapper.MtOpenGiftMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 开卡赠礼仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtOpenGiftRepositoryImpl implements MtOpenGiftRepository {

    private final MtOpenGiftMapper mtOpenGiftMapper;
    private final MtOpenGiftConverter mtOpenGiftConverter;

    public MtOpenGiftRepositoryImpl(MtOpenGiftMapper mtOpenGiftMapper, MtOpenGiftConverter mtOpenGiftConverter) {
        this.mtOpenGiftMapper = mtOpenGiftMapper;
        this.mtOpenGiftConverter = mtOpenGiftConverter;
    }

    @Override
    public MtOpenGift save(MtOpenGift mtOpenGift) {
        MtOpenGiftDO mtOpenGiftDO = mtOpenGiftConverter.toDataObject(mtOpenGift);
        if (mtOpenGiftDO.getId() == null) {
            mtOpenGiftMapper.insert(mtOpenGiftDO);
        } else {
            mtOpenGiftMapper.updateById(mtOpenGiftDO);
        }
        return mtOpenGiftConverter.toDomain(mtOpenGiftDO);
    }

    @Override
    public Optional<MtOpenGift> findById(Long id) {
        MtOpenGiftDO mtOpenGiftDO = mtOpenGiftMapper.selectById(id);
        return Optional.ofNullable(mtOpenGiftConverter.toDomain(mtOpenGiftDO));
    }

    @Override
    public List<MtOpenGift> findAll() {
        List<MtOpenGiftDO> mtOpenGiftDOS = mtOpenGiftMapper.selectList(null);
        return mtOpenGiftDOS.stream()
                .map(mtOpenGiftConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtOpenGiftMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtOpenGiftDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtOpenGiftDO::getId, id);
        return mtOpenGiftMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtOpenGift> findByStatus(String status) {
        LambdaQueryWrapper<MtOpenGiftDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtOpenGiftDO::getStatus, status);
        List<MtOpenGiftDO> mtOpenGiftDOS = mtOpenGiftMapper.selectList(wrapper);
        return mtOpenGiftDOS.stream()
                .map(mtOpenGiftConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MtOpenGift> findByGradeId(Long gradeId) {
        LambdaQueryWrapper<MtOpenGiftDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtOpenGiftDO::getGradeId, gradeId);
        MtOpenGiftDO mtOpenGiftDO = mtOpenGiftMapper.selectOne(wrapper);
        return Optional.ofNullable(mtOpenGiftConverter.toDomain(mtOpenGiftDO));
    }
}
