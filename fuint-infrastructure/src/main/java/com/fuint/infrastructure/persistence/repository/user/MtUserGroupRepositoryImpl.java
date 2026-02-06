package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtUserGroup;
import com.fuint.domain.repository.user.MtUserGroupRepository;
import com.fuint.infrastructure.persistence.converter.MtUserGroupConverter;
import com.fuint.infrastructure.persistence.entity.MtUserGroupDO;
import com.fuint.infrastructure.persistence.mapper.MtUserGroupMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员分组仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtUserGroupRepositoryImpl implements MtUserGroupRepository {

    private final MtUserGroupMapper mtUserGroupMapper;
    private final MtUserGroupConverter mtUserGroupConverter;

    public MtUserGroupRepositoryImpl(MtUserGroupMapper mtUserGroupMapper, MtUserGroupConverter mtUserGroupConverter) {
        this.mtUserGroupMapper = mtUserGroupMapper;
        this.mtUserGroupConverter = mtUserGroupConverter;
    }

    @Override
    public MtUserGroup save(MtUserGroup mtUserGroup) {
        MtUserGroupDO mtUserGroupDO = mtUserGroupConverter.toDataObject(mtUserGroup);
        if (mtUserGroupDO.getId() == null) {
            mtUserGroupMapper.insert(mtUserGroupDO);
        } else {
            mtUserGroupMapper.updateById(mtUserGroupDO);
        }
        return mtUserGroupConverter.toDomain(mtUserGroupDO);
    }

    @Override
    public Optional<MtUserGroup> findById(Long id) {
        MtUserGroupDO mtUserGroupDO = mtUserGroupMapper.selectById(id);
        return Optional.ofNullable(mtUserGroupConverter.toDomain(mtUserGroupDO));
    }

    @Override
    public List<MtUserGroup> findAll() {
        List<MtUserGroupDO> mtUserGroupDOS = mtUserGroupMapper.selectList(null);
        return mtUserGroupDOS.stream()
                .map(mtUserGroupConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtUserGroupMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtUserGroupDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserGroupDO::getId, id);
        return mtUserGroupMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtUserGroup> findByStatus(String status) {
        LambdaQueryWrapper<MtUserGroupDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserGroupDO::getStatus, status);
        List<MtUserGroupDO> mtUserGroupDOS = mtUserGroupMapper.selectList(wrapper);
        return mtUserGroupDOS.stream()
                .map(mtUserGroupConverter::toDomain)
                .collect(Collectors.toList());
    }
}
