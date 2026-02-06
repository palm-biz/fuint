package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtUserAction;
import com.fuint.domain.repository.user.MtUserActionRepository;
import com.fuint.infrastructure.persistence.converter.MtUserActionConverter;
import com.fuint.infrastructure.persistence.entity.MtUserActionDO;
import com.fuint.infrastructure.persistence.mapper.MtUserActionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员行为仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtUserActionRepositoryImpl implements MtUserActionRepository {

    private final MtUserActionMapper mtUserActionMapper;
    private final MtUserActionConverter mtUserActionConverter;

    public MtUserActionRepositoryImpl(MtUserActionMapper mtUserActionMapper, MtUserActionConverter mtUserActionConverter) {
        this.mtUserActionMapper = mtUserActionMapper;
        this.mtUserActionConverter = mtUserActionConverter;
    }

    @Override
    public MtUserAction save(MtUserAction mtUserAction) {
        MtUserActionDO mtUserActionDO = mtUserActionConverter.toDataObject(mtUserAction);
        if (mtUserActionDO.getId() == null) {
            mtUserActionMapper.insert(mtUserActionDO);
        } else {
            mtUserActionMapper.updateById(mtUserActionDO);
        }
        return mtUserActionConverter.toDomain(mtUserActionDO);
    }

    @Override
    public Optional<MtUserAction> findById(Long id) {
        MtUserActionDO mtUserActionDO = mtUserActionMapper.selectById(id);
        return Optional.ofNullable(mtUserActionConverter.toDomain(mtUserActionDO));
    }

    @Override
    public List<MtUserAction> findAll() {
        List<MtUserActionDO> mtUserActionDOS = mtUserActionMapper.selectList(null);
        return mtUserActionDOS.stream()
                .map(mtUserActionConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtUserActionMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtUserActionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserActionDO::getId, id);
        return mtUserActionMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtUserAction> findByUserId(Long userId) {
        LambdaQueryWrapper<MtUserActionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserActionDO::getUserId, userId);
        List<MtUserActionDO> mtUserActionDOS = mtUserActionMapper.selectList(wrapper);
        return mtUserActionDOS.stream()
                .map(mtUserActionConverter::toDomain)
                .collect(Collectors.toList());
    }
}
