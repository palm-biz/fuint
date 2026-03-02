package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.enums.YesOrNoEnum;
import cloud.palmbiz.common.service.MerchantService;
import cloud.palmbiz.common.utils.StringUtil;
import cloud.palmbiz.domain.store.model.Store;
import cloud.palmbiz.domain.store.model.StoreId;
import cloud.palmbiz.domain.store.repository.StoreRepository;
import cloud.palmbiz.infrastructure.bean.StoreDistanceBean;
import cloud.palmbiz.infrastructure.mapper.MtMerchantMapper;
import cloud.palmbiz.infrastructure.mapper.MtStoreGoodsMapper;
import cloud.palmbiz.infrastructure.mapper.MtStoreMapper;
import cloud.palmbiz.infrastructure.model.MtMerchant;
import cloud.palmbiz.infrastructure.model.MtStore;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 店铺仓储实现
 */
@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final MtStoreMapper mtStoreMapper;
    private final MtMerchantMapper mtMerchantMapper;
    private final MtStoreGoodsMapper mtStoreGoodsMapper;
    private final MerchantService merchantService;

    @Override
    public Store findById(StoreId storeId) {
        MtStore mtStore = mtStoreMapper.selectById(storeId.getValue());
        return mtStore != null ? toDomain(mtStore) : null;
    }

    @Override
    public Store findByName(String storeName) {
        MtStore mtStore = mtStoreMapper.queryStoreByName(storeName);
        return mtStore != null ? toDomain(mtStore) : null;
    }

    @Override
    public Store findDefaultByMerchantNo(String merchantNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", StatusEnum.ENABLED.getKey());
        params.put("is_default", YesOrNoEnum.YES.getKey());

        if (StringUtil.isNotEmpty(merchantNo)) {
            MtMerchant mtMerchant = merchantService.queryMerchantByNo(merchantNo);
            if (mtMerchant != null) {
                params.put("merchantId", mtMerchant.getId());
            }
        }

        List<Store> storeList = findByParams(params);
        if (!storeList.isEmpty()) {
            return storeList.get(0);
        }

        // 如果没有默认店铺，返回第一个启用的店铺
        Map<String, Object> param = new HashMap<>();
        param.put("status", StatusEnum.ENABLED.getKey());
        List<Store> dataList = findByParams(param);
        return !dataList.isEmpty() ? dataList.get(0) : null;
    }

    @Override
    public List<Store> findByParams(Map<String, Object> params) {
        LambdaQueryWrapper<MtStore> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(MtStore::getStatus, StatusEnum.DISABLE.getKey());

        String storeId = params.get("storeId") == null ? "" : params.get("storeId").toString();
        if (StringUtils.isNotBlank(storeId)) {
            wrapper.eq(MtStore::getId, storeId);
        }

        String name = params.get("name") == null ? "" : params.get("name").toString();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(MtStore::getName, name);
        }

        String status = params.get("status") == null ? "" : params.get("status").toString();
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(MtStore::getStatus, status);
        }

        String merchantId = params.get("merchantId") == null ? "" : params.get("merchantId").toString();
        if (StringUtils.isNotBlank(merchantId)) {
            wrapper.eq(MtStore::getMerchantId, merchantId);
        }

        String isDefault = params.get("is_default") == null ? "" : params.get("is_default").toString();
        if (StringUtils.isNotBlank(isDefault)) {
            wrapper.eq(MtStore::getIsDefault, isDefault);
        }

        wrapper.orderByAsc(MtStore::getStatus).orderByDesc(MtStore::getIsDefault);
        List<MtStore> storeList = mtStoreMapper.selectList(wrapper);

        return storeList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> findByDistance(Integer merchantId, String keyword, String latitude, String longitude) {
        List<StoreDistanceBean> distanceList = mtStoreMapper.queryByDistance(merchantId, keyword, latitude, longitude);

        Map<String, Object> param = new HashMap<>();
        param.put("status", StatusEnum.ENABLED.getKey());
        if (merchantId != null && merchantId > 0) {
            param.put("merchant_id", merchantId);
        }
        List<MtStore> storeList = mtStoreMapper.selectByMap(param);

        List<Store> result = new ArrayList<>();
        if (distanceList != null) {
            for (StoreDistanceBean bean : distanceList) {
                for (MtStore mtStore : storeList) {
                    if (mtStore.getId().equals(bean.getId())) {
                        Store store = toDomain(mtStore);
                        if (StringUtil.isNotEmpty(latitude) && StringUtil.isNotEmpty(longitude)) {
                            store.setDistance(new BigDecimal(bean.getDistance()));
                        } else {
                            store.setDistance(new BigDecimal("0.0"));
                        }
                        result.add(store);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<Store> findMyStores(Integer merchantId, Integer storeId, String status) {
        List<MtStore> storeList = mtStoreMapper.getMyStoreList(merchantId, storeId, status);
        return storeList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Store store) {
        MtStore mtStore = toPO(store);
        if (mtStore.getId() == null || mtStore.getId() < 1) {
            mtStoreMapper.insert(mtStore);
            // 更新领域对象的ID
            // store.setId(mtStore.getId());  // 需要在Store中添加setId方法或通过反射设置
        } else {
            mtStoreMapper.updateById(mtStore);
        }
    }

    @Override
    public void remove(Store store) {
        if (store.getId() != null) {
            mtStoreMapper.deleteById(store.getId());
        }
    }

    @Override
    public void resetDefaultStore(Integer merchantId) {
        mtStoreMapper.resetDefaultStore(merchantId);
    }

    @Override
    public void removeByMerchantId(Integer merchantId) {
        if (merchantId != null && merchantId > 0) {
            mtStoreMapper.deleteStoreByMerchant(merchantId);
        }
    }

    @Override
    public void removeStoreGoods(Integer storeId) {
        if (storeId != null) {
            mtStoreGoodsMapper.removeStoreGoods(storeId);
        }
    }

    /**
     * 领域对象转PO
     */
    private MtStore toPO(Store store) {
        MtStore po = new MtStore();
        po.setId(store.getId());
        po.setMerchantId(store.getMerchantId());
        po.setName(store.getName());
        po.setLogo(store.getLogo());
        po.setQrCode(store.getQrCode());
        po.setIsDefault(store.getIsDefault());
        po.setContact(store.getContact());
        po.setPhone(store.getPhone());
        po.setAddress(store.getAddress());
        po.setLatitude(store.getLatitude());
        po.setLongitude(store.getLongitude());
        po.setDistance(store.getDistance());
        po.setHours(store.getHours());
        po.setLicense(store.getLicense());
        po.setCreditCode(store.getCreditCode());

        if (store.getBankAccount() != null) {
            po.setBankName(store.getBankAccount().getBankName());
            po.setBankCardName(store.getBankAccount().getBankCardName());
            po.setBankCardNo(store.getBankAccount().getBankCardNo());
        }

        if (store.getPaymentConfig() != null) {
            po.setWxMchId(store.getPaymentConfig().getWxMchId());
            po.setWxApiV2(store.getPaymentConfig().getWxApiV2());
            po.setWxCertPath(store.getPaymentConfig().getWxCertPath());
            po.setAlipayAppId(store.getPaymentConfig().getAlipayAppId());
            po.setAlipayPrivateKey(store.getPaymentConfig().getAlipayPrivateKey());
            po.setAlipayPublicKey(store.getPaymentConfig().getAlipayPublicKey());
        }

        po.setDescription(store.getDescription());
        po.setCreateTime(store.getCreateTime());
        po.setUpdateTime(store.getUpdateTime());
        po.setStatus(store.getStatus().getCode());
        po.setOperator(store.getOperator());

        return po;
    }

    /**
     * PO转领域对象
     */
    private Store toDomain(MtStore po) {
        return Store.reconstitute(
            po.getId(),
            po.getMerchantId(),
            po.getName(),
            po.getLogo(),
            po.getQrCode(),
            po.getIsDefault(),
            po.getContact(),
            po.getPhone(),
            po.getAddress(),
            po.getLatitude(),
            po.getLongitude(),
            po.getDistance(),
            po.getHours(),
            po.getLicense(),
            po.getCreditCode(),
            po.getBankName(),
            po.getBankCardName(),
            po.getBankCardNo(),
            po.getWxMchId(),
            po.getWxApiV2(),
            po.getWxCertPath(),
            po.getAlipayAppId(),
            po.getAlipayPrivateKey(),
            po.getAlipayPublicKey(),
            po.getDescription(),
            po.getCreateTime(),
            po.getUpdateTime(),
            po.getStatus(),
            po.getOperator()
        );
    }
}
