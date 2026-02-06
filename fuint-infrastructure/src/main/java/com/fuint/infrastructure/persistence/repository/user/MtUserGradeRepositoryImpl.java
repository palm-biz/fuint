package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtUserGrade;
import com.fuint.domain.repository.user.MtUserGradeRepository;
import com.fuint.infrastructure.persistence.converter.MtUserGradeConverter;
import com.fuint.infrastructure.persistence.entity.MtUserGradeDO;
import com.fuint.infrastructure.persistence.mapper.MtUserGradeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员等级仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtUserGradeRepositoryImpl implements MtUserGradeRepository {

    private final MtUserGradeMapper mtUserGradeMapper;
    private final MtUserGradeConverter mtUserGradeConverter;

    public MtUserGradeRepositoryImpl(MtUserGradeMapper mtUserGradeMapper, MtUserGradeConverter mtUserGradeConverter) {
        this.mtUserGradeMapper = mtUserGradeMapper;
        this.mtUserGradeConverter = mtUserGradeConverter;
    }

    @Override
    public MtUserGrade save(MtUserGrade mtUserGrade) {
        MtUserGradeDO mtUserGradeDO = mtUserGradeConverter.toDataObject(mtUserGrade);
        if (mtUserGradeDO.getId() == null) {
            mtUserGradeMapper.insert(mtUserGradeDO);
        } else {
            mtUserGradeMapper.updateById(mtUserGradeDO);
        }
        return mtUserGradeConverter.toDomain(mtUserGradeDO);
    }

    @Override
    public Optional<MtUserGrade> findById(Long id) {
        MtUserGradeDO mtUserGradeDO = mtUserGradeMapper.selectById(id);
        return Optional.ofNullable(mtUserGradeConverter.toDomain(mtUserGradeDO));
    }

    @Override
    public List<MtUserGrade> findAll() {
        List<MtUserGradeDO> mtUserGradeDOS = mtUserGradeMapper.selectList(null);
        return mtUserGradeDOS.stream()
                .map(mtUserGradeConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtUserGradeMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtUserGradeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserGradeDO::getId, id);
        return mtUserGradeMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtUserGrade> findByStatus(String status) {
        LambdaQueryWrapper<MtUserGradeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserGradeDO::getStatus, status);
        List<MtUserGradeDO> mtUserGradeDOS = mtUserGradeMapper.selectList(wrapper);
        return mtUserGradeDOS.stream()
                .map(mtUserGradeConverter::toDomain)
                .collect(Collectors.toList());
    }
}
