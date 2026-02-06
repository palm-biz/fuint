package com.fuint.infrastructure.persistence.repository.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.order.MtOrderAddress;
import com.fuint.domain.repository.order.MtOrderAddressRepository;
import com.fuint.infrastructure.persistence.converter.MtOrderAddressConverter;
import com.fuint.infrastructure.persistence.entity.MtOrderAddressDO;
import com.fuint.infrastructure.persistence.mapper.MtOrderAddressMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MtOrderAddressRepositoryImpl implements MtOrderAddressRepository {
    private final MtOrderAddressMapper mtOrderAddressMapper;
    private final MtOrderAddressConverter mtOrderAddressConverter;

    public MtOrderAddressRepositoryImpl(MtOrderAddressMapper mtOrderAddressMapper, MtOrderAddressConverter mtOrderAddressConverter) {
        this.mtOrderAddressMapper = mtOrderAddressMapper;
        this.mtOrderAddressConverter = mtOrderAddressConverter;
    }

    @Override
    public MtOrderAddress save(MtOrderAddress mtOrderAddress) {
        MtOrderAddressDO mtOrderAddressDO = mtOrderAddressConverter.toDataObject(mtOrderAddress);
        if (mtOrderAddressDO.getId() == null) {
            mtOrderAddressMapper.insert(mtOrderAddressDO);
        } else {
            mtOrderAddressMapper.updateById(mtOrderAddressDO);
        }
        return mtOrderAddressConverter.toDomain(mtOrderAddressDO);
    }

    @Override
    public Optional<MtOrderAddress> findById(Long id) {
        MtOrderAddressDO mtOrderAddressDO = mtOrderAddressMapper.selectById(id);
        return Optional.ofNullable(mtOrderAddressConverter.toDomain(mtOrderAddressDO));
    }

    @Override
    public List<MtOrderAddress> findAll() {
        List<MtOrderAddressDO> mtOrderAddressDOS = mtOrderAddressMapper.selectList(null);
        return mtOrderAddressDOS.stream().map(mtOrderAddressConverter::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        mtOrderAddressMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mtOrderAddressMapper.selectCount(new LambdaQueryWrapper<MtOrderAddressDO>().eq(MtOrderAddressDO::getId, id)) > 0;
    }

    @Override
    public Optional<MtOrderAddress> findByOrderId(Long orderId) {
        MtOrderAddressDO mtOrderAddressDO = mtOrderAddressMapper.selectOne(new LambdaQueryWrapper<MtOrderAddressDO>().eq(MtOrderAddressDO::getOrderId, orderId));
        return Optional.ofNullable(mtOrderAddressConverter.toDomain(mtOrderAddressDO));
    }
}
