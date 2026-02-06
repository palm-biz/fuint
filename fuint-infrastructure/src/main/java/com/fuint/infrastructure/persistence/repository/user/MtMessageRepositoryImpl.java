package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtMessage;
import com.fuint.domain.repository.user.MtMessageRepository;
import com.fuint.infrastructure.persistence.converter.MtMessageConverter;
import com.fuint.infrastructure.persistence.entity.MtMessageDO;
import com.fuint.infrastructure.persistence.mapper.MtMessageMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员消息仓储实现
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtMessageRepositoryImpl implements MtMessageRepository {

    private final MtMessageMapper mtMessageMapper;
    private final MtMessageConverter mtMessageConverter;

    public MtMessageRepositoryImpl(MtMessageMapper mtMessageMapper, MtMessageConverter mtMessageConverter) {
        this.mtMessageMapper = mtMessageMapper;
        this.mtMessageConverter = mtMessageConverter;
    }

    @Override
    public MtMessage save(MtMessage mtMessage) {
        MtMessageDO mtMessageDO = mtMessageConverter.toDataObject(mtMessage);
        if (mtMessageDO.getId() == null) {
            mtMessageMapper.insert(mtMessageDO);
        } else {
            mtMessageMapper.updateById(mtMessageDO);
        }
        return mtMessageConverter.toDomain(mtMessageDO);
    }

    @Override
    public Optional<MtMessage> findById(Long id) {
        MtMessageDO mtMessageDO = mtMessageMapper.selectById(id);
        return Optional.ofNullable(mtMessageConverter.toDomain(mtMessageDO));
    }

    @Override
    public List<MtMessage> findAll() {
        List<MtMessageDO> mtMessageDOS = mtMessageMapper.selectList(null);
        return mtMessageDOS.stream()
                .map(mtMessageConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtMessageMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtMessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtMessageDO::getId, id);
        return mtMessageMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtMessage> findByUserId(Long userId) {
        LambdaQueryWrapper<MtMessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtMessageDO::getUserId, userId);
        List<MtMessageDO> mtMessageDOS = mtMessageMapper.selectList(wrapper);
        return mtMessageDOS.stream()
                .map(mtMessageConverter::toDomain)
                .collect(Collectors.toList());
    }
}
