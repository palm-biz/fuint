package com.fuint.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuint.domain.model.merchant.Merchant;
import com.fuint.domain.model.merchant.MerchantStatus;
import com.fuint.domain.repository.MerchantRepository;
import com.fuint.infrastructure.persistence.converter.MerchantConverter;
import com.fuint.infrastructure.persistence.entity.MerchantDO;
import com.fuint.infrastructure.persistence.mapper.MerchantMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商户仓储实现
 * 实现领域层定义的接口
 *
 * @author fuint
 * @since 2.0.0
 */
@Repository
public class MerchantRepositoryImpl implements MerchantRepository {

    private final MerchantMapper merchantMapper;
    private final MerchantConverter merchantConverter;

    public MerchantRepositoryImpl(MerchantMapper merchantMapper, MerchantConverter merchantConverter) {
        this.merchantMapper = merchantMapper;
        this.merchantConverter = merchantConverter;
    }

    @Override
    public Merchant save(Merchant merchant) {
        MerchantDO merchantDO = merchantConverter.toDataObject(merchant);

        if (merchantDO.getId() == null) {
            // 新增
            merchantMapper.insert(merchantDO);
        } else {
            // 更新
            merchantMapper.updateById(merchantDO);
        }

        return merchantConverter.toDomain(merchantDO);
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        MerchantDO merchantDO = merchantMapper.selectById(id);
        return Optional.ofNullable(merchantConverter.toDomain(merchantDO));
    }

    @Override
    public Optional<Merchant> findByTenantId(Long tenantId) {
        LambdaQueryWrapper<MerchantDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MerchantDO::getTenantId, tenantId);

        MerchantDO merchantDO = merchantMapper.selectOne(wrapper);
        return Optional.ofNullable(merchantConverter.toDomain(merchantDO));
    }

    @Override
    public List<Merchant> findAllActive() {
        LambdaQueryWrapper<MerchantDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MerchantDO::getStatus, MerchantStatus.ACTIVE.getCode());

        List<MerchantDO> merchantDOS = merchantMapper.selectList(wrapper);
        return merchantDOS.stream()
                .map(merchantConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        merchantMapper.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        LambdaQueryWrapper<MerchantDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MerchantDO::getId, id);
        return merchantMapper.selectCount(wrapper) > 0;
    }
}
