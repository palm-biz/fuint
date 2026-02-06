package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtUser;
import com.fuint.domain.repository.user.MtUserRepository;
import com.fuint.infrastructure.persistence.converter.MtUserConverter;
import com.fuint.infrastructure.persistence.entity.MtUserDO;
import com.fuint.infrastructure.persistence.mapper.MtUserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员仓储实现
 * 实现领域层定义的接口
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtUserRepositoryImpl implements MtUserRepository {

    private final MtUserMapper mtUserMapper;
    private final MtUserConverter mtUserConverter;

    public MtUserRepositoryImpl(MtUserMapper mtUserMapper, MtUserConverter mtUserConverter) {
        this.mtUserMapper = mtUserMapper;
        this.mtUserConverter = mtUserConverter;
    }

    @Override
    public MtUser save(MtUser mtUser) {
        MtUserDO mtUserDO = mtUserConverter.toDataObject(mtUser);

        if (mtUserDO.getId() == null) {
            // 新增
            mtUserMapper.insert(mtUserDO);
        } else {
            // 更新
            mtUserMapper.updateById(mtUserDO);
        }

        return mtUserConverter.toDomain(mtUserDO);
    }

    @Override
    public Optional<MtUser> findById(Long id) {
        MtUserDO mtUserDO = mtUserMapper.selectById(id);
        return Optional.ofNullable(mtUserConverter.toDomain(mtUserDO));
    }

    @Override
    public List<MtUser> findAll() {
        List<MtUserDO> mtUserDOS = mtUserMapper.selectList(null);
        return mtUserDOS.stream()
                .map(mtUserConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtUserMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserDO::getId, id);
        return mtUserMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Optional<MtUser> findByMobile(String mobile) {
        LambdaQueryWrapper<MtUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserDO::getMobile, mobile);

        MtUserDO mtUserDO = mtUserMapper.selectOne(wrapper);
        return Optional.ofNullable(mtUserConverter.toDomain(mtUserDO));
    }

    @Override
    public Optional<MtUser> findByOpenId(String openId) {
        LambdaQueryWrapper<MtUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserDO::getOpenId, openId);

        MtUserDO mtUserDO = mtUserMapper.selectOne(wrapper);
        return Optional.ofNullable(mtUserConverter.toDomain(mtUserDO));
    }

    @Override
    public List<MtUser> findByStatus(String status) {
        LambdaQueryWrapper<MtUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtUserDO::getStatus, status);

        List<MtUserDO> mtUserDOS = mtUserMapper.selectList(wrapper);
        return mtUserDOS.stream()
                .map(mtUserConverter::toDomain)
                .collect(Collectors.toList());
    }
}
