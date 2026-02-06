package com.fuint.infrastructure.persistence.repository.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.user.MtAddress;
import com.fuint.domain.repository.user.MtAddressRepository;
import com.fuint.infrastructure.persistence.converter.MtAddressConverter;
import com.fuint.infrastructure.persistence.entity.MtAddressDO;
import com.fuint.infrastructure.persistence.mapper.MtAddressMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会员地址仓储实现
 * 实现领域层定义的接口
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MtAddressRepositoryImpl implements MtAddressRepository {

    private final MtAddressMapper mtAddressMapper;
    private final MtAddressConverter mtAddressConverter;

    public MtAddressRepositoryImpl(MtAddressMapper mtAddressMapper, MtAddressConverter mtAddressConverter) {
        this.mtAddressMapper = mtAddressMapper;
        this.mtAddressConverter = mtAddressConverter;
    }

    @Override
    public MtAddress save(MtAddress mtAddress) {
        MtAddressDO mtAddressDO = mtAddressConverter.toDataObject(mtAddress);

        if (mtAddressDO.getId() == null) {
            // 新增
            mtAddressMapper.insert(mtAddressDO);
        } else {
            // 更新
            mtAddressMapper.updateById(mtAddressDO);
        }

        return mtAddressConverter.toDomain(mtAddressDO);
    }

    @Override
    public Optional<MtAddress> findById(Long id) {
        MtAddressDO mtAddressDO = mtAddressMapper.selectById(id);
        return Optional.ofNullable(mtAddressConverter.toDomain(mtAddressDO));
    }

    @Override
    public List<MtAddress> findAll() {
        List<MtAddressDO> mtAddressDOS = mtAddressMapper.selectList(null);
        return mtAddressDOS.stream()
                .map(mtAddressConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtAddressMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MtAddressDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtAddressDO::getId, id);
        return mtAddressMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<MtAddress> findByUserId(Long userId) {
        LambdaQueryWrapper<MtAddressDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtAddressDO::getUserId, userId);

        List<MtAddressDO> mtAddressDOS = mtAddressMapper.selectList(wrapper);
        return mtAddressDOS.stream()
                .map(mtAddressConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MtAddress> findDefaultByUserId(Long userId) {
        LambdaQueryWrapper<MtAddressDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MtAddressDO::getUserId, userId);
        wrapper.eq(MtAddressDO::getIsDefault, "Y");

        MtAddressDO mtAddressDO = mtAddressMapper.selectOne(wrapper);
        return Optional.ofNullable(mtAddressConverter.toDomain(mtAddressDO));
    }
}
