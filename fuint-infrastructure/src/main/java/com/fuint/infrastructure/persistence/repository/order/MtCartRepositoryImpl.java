package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtCart;
import com.fuint.domain.repository.order.MtCartRepository;
import com.fuint.infrastructure.persistence.converter.MtCartConverter;
import com.fuint.infrastructure.persistence.entity.MtCartDO;
import com.fuint.infrastructure.persistence.mapper.MtCartMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtCartRepositoryImpl implements MtCartRepository {
    private final MtCartMapper mtCartMapper;
    private final MtCartConverter mtCartConverter;

    public MtCartRepositoryImpl(MtCartMapper mtCartMapper, MtCartConverter mtCartConverter) {
        this.mtCartMapper = mtCartMapper;
        this.mtCartConverter = mtCartConverter;
    }

    @Override
    public MtCart save(MtCart mtCart) {
        MtCartDO mtCartDO = mtCartConverter.toDataObject(mtCart);
        if (mtCartDO.getId() == null) {
            mtCartMapper.insert(mtCartDO);
        } else {
            mtCartMapper.updateById(mtCartDO);
        }
        return mtCartConverter.toDomain(mtCartDO);
    }

    @Override
    public Optional<MtCart> findById(Long id) {
        MtCartDO mtCartDO = mtCartMapper.selectById(id);
        return Optional.ofNullable(mtCartConverter.toDomain(mtCartDO));
    }

    @Override
    public List<MtCart> findAll() {
        List<MtCartDO> mtCartDOS = mtCartMapper.selectList(null);
        return mtCartDOS.stream().map(mtCartConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtCartMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtCartMapper.selectCount(new LambdaQueryWrapper<MtCartDO>().eq(MtCartDO::getId, id)) > 0;
    }

    @Override
    public List<MtCart> findByUserId(Long userId) {
        List<MtCartDO> mtCartDOS = mtCartMapper.selectList(new LambdaQueryWrapper<MtCartDO>().eq(MtCartDO::getUserId, userId));
        return mtCartDOS.stream().map(mtCartConverter::toDomain).collect(Collectors.toList());
    }
}
