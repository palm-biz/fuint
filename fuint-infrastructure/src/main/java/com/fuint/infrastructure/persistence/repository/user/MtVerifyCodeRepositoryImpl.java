package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtVerifyCode;
import com.fuint.domain.repository.user.MtVerifyCodeRepository;
import com.fuint.infrastructure.persistence.converter.MtVerifyCodeConverter;
import com.fuint.infrastructure.persistence.entity.MtVerifyCodeDO;
import com.fuint.infrastructure.persistence.mapper.MtVerifyCodeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 验证码仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtVerifyCodeRepositoryImpl implements MtVerifyCodeRepository {

    private final MtVerifyCodeMapper mtVerifyCodeMapper;
    private final MtVerifyCodeConverter mtVerifyCodeConverter;

    public MtVerifyCodeRepositoryImpl(MtVerifyCodeMapper mtVerifyCodeMapper, MtVerifyCodeConverter mtVerifyCodeConverter) {
        this.mtVerifyCodeMapper = mtVerifyCodeMapper;
        this.mtVerifyCodeConverter = mtVerifyCodeConverter;
    }

    @Override
    public MtVerifyCode save(MtVerifyCode mtVerifyCode) {
        MtVerifyCodeDO mtVerifyCodeDO = mtVerifyCodeConverter.toDataObject(mtVerifyCode);
        if (mtVerifyCodeDO.getId() == null) {
            mtVerifyCodeMapper.insert(mtVerifyCodeDO);
        } else {
            mtVerifyCodeMapper.updateById(mtVerifyCodeDO);
        }
        return mtVerifyCodeConverter.toDomain(mtVerifyCodeDO);
    }

    @Override
    public Optional<MtVerifyCode> findById(Long id) {
        MtVerifyCodeDO mtVerifyCodeDO = mtVerifyCodeMapper.selectById(id);
        return Optional.ofNullable(mtVerifyCodeConverter.toDomain(mtVerifyCodeDO));
    }

    @Override
    public List<MtVerifyCode> findAll() {
        List<MtVerifyCodeDO> mtVerifyCodeDOS = mtVerifyCodeMapper.selectList(null);
        return mtVerifyCodeDOS.stream()
                .map(mtVerifyCodeConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtVerifyCodeMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtVerifyCodeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtVerifyCodeDO::getId, id);
        return mtVerifyCodeMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Optional<MtVerifyCode> findByMobile(String mobile) {
        LambdaQueryWrapper<MtVerifyCodeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtVerifyCodeDO::getMobile, mobile);
        wrapper.orderByDesc(MtVerifyCodeDO::getCreateTime);
        wrapper.last("LIMIT 1");
        MtVerifyCodeDO mtVerifyCodeDO = mtVerifyCodeMapper.selectOne(wrapper);
        return Optional.ofNullable(mtVerifyCodeConverter.toDomain(mtVerifyCodeDO));
    }
}
