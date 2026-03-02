package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.domain.merchant.model.Merchant;
import cloud.palmbiz.domain.merchant.model.MerchantId;
import cloud.palmbiz.domain.merchant.repository.MerchantRepository;
import cloud.palmbiz.infrastructure.mapper.MtMerchantMapper;
import cloud.palmbiz.infrastructure.model.MtMerchant;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商户仓储实现
 */
@Repository
@RequiredArgsConstructor
public class MerchantRepositoryImpl implements MerchantRepository {

    private final MtMerchantMapper mtMerchantMapper;

    @Override
    public Merchant findById(MerchantId merchantId) {
        MtMerchant mtMerchant = mtMerchantMapper.selectById(merchantId.getValue());
        return mtMerchant != null ? toDomain(mtMerchant) : null;
    }

    @Override
    public Merchant findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<MtMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtMerchant::getName, name.trim());
        wrapper.ne(MtMerchant::getStatus, StatusEnum.DISABLE.getKey());
        wrapper.last("LIMIT 1");

        MtMerchant mtMerchant = mtMerchantMapper.selectOne(wrapper);
        return mtMerchant != null ? toDomain(mtMerchant) : null;
    }

    @Override
    public Merchant findByNo(String merchantNo) {
        if (merchantNo == null || merchantNo.trim().isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<MtMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtMerchant::getNo, merchantNo.trim());
        wrapper.ne(MtMerchant::getStatus, StatusEnum.DISABLE.getKey());
        wrapper.last("LIMIT 1");

        MtMerchant mtMerchant = mtMerchantMapper.selectOne(wrapper);
        return mtMerchant != null ? toDomain(mtMerchant) : null;
    }

    @Override
    public List<Merchant> findByParams(Map<String, Object> params) {
        LambdaQueryWrapper<MtMerchant> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(MtMerchant::getStatus, StatusEnum.DISABLE.getKey());

        String id = params.get("id") == null ? "" : params.get("id").toString();
        if (StringUtils.isNotBlank(id)) {
            wrapper.eq(MtMerchant::getId, id);
        }

        String name = params.get("name") == null ? "" : params.get("name").toString();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(MtMerchant::getName, name);
        }

        String status = params.get("status") == null ? "" : params.get("status").toString();
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(MtMerchant::getStatus, status);
        }

        String merchantNo = params.get("no") == null ? "" : params.get("no").toString();
        if (StringUtils.isNotBlank(merchantNo)) {
            wrapper.eq(MtMerchant::getNo, merchantNo);
        }

        wrapper.orderByAsc(MtMerchant::getStatus).orderByDesc(MtMerchant::getId);
        List<MtMerchant> merchantList = mtMerchantMapper.selectList(wrapper);

        return merchantList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Merchant> findMyMerchants(Integer merchantId, Integer storeId, String status) {
        List<MtMerchant> merchantList = mtMerchantMapper.getMyMerchantList(merchantId, storeId, status);
        return merchantList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Merchant merchant) {
        MtMerchant mtMerchant = toPO(merchant);
        if (mtMerchant.getId() == null || mtMerchant.getId() < 1) {
            mtMerchantMapper.insert(mtMerchant);
        } else {
            mtMerchantMapper.updateById(mtMerchant);
        }
    }

    @Override
    public void remove(Merchant merchant) {
        if (merchant.getId() != null) {
            mtMerchantMapper.deleteById(merchant.getId());
        }
    }

    /**
     * 领域对象转PO
     */
    private MtMerchant toPO(Merchant merchant) {
        MtMerchant po = new MtMerchant();
        po.setId(merchant.getId());
        po.setNo(merchant.getNo());
        po.setName(merchant.getName());
        po.setType(merchant.getType());
        po.setLogo(merchant.getLogo());
        po.setContact(merchant.getContact());
        po.setPhone(merchant.getPhone());
        po.setAddress(merchant.getAddress());
        po.setWxAppId(merchant.getWxAppId());
        po.setWxAppSecret(merchant.getWxAppSecret());
        po.setWxOfficialAppId(merchant.getWxOfficialAppId());
        po.setWxOfficialAppSecret(merchant.getWxOfficialAppSecret());
        po.setSettleRate(merchant.getSettleRateValue());
        po.setDescription(merchant.getDescription());
        po.setCreateTime(merchant.getCreateTime());
        po.setUpdateTime(merchant.getUpdateTime());
        po.setStatus(merchant.getStatus().getCode());
        po.setOperator(merchant.getOperator());
        return po;
    }

    /**
     * PO转领域对象
     */
    private Merchant toDomain(MtMerchant po) {
        return Merchant.reconstitute(
            po.getId(),
            po.getNo(),
            po.getName(),
            po.getType(),
            po.getLogo(),
            po.getContact(),
            po.getPhone(),
            po.getAddress(),
            po.getWxAppId(),
            po.getWxAppSecret(),
            po.getWxOfficialAppId(),
            po.getWxOfficialAppSecret(),
            po.getSettleRate(),
            po.getDescription(),
            po.getCreateTime(),
            po.getUpdateTime(),
            po.getStatus(),
            po.getOperator()
        );
    }
}
