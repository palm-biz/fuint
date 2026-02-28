package cloud.palmbiz.application.store.service;

import cloud.palmbiz.application.store.query.StoreQuery;
import cloud.palmbiz.common.store.dto.StoreDto;
import cloud.palmbiz.common.store.dto.StoreInfo;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.domain.store.model.Store;
import cloud.palmbiz.domain.store.model.StoreId;
import cloud.palmbiz.domain.store.repository.StoreRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.mapper.MtMerchantMapper;
import cloud.palmbiz.infrastructure.model.MtMerchant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 店铺查询服务
 */
@Service
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreRepository storeRepository;
    private final MtMerchantMapper mtMerchantMapper;

    /**
     * 分页查询店铺列表
     */
    public PaginationResponse<StoreDto> queryByPage(PaginationRequest paginationRequest) {
        Page<Store> pageHelper = PageHelper.startPage(paginationRequest.getCurrentPage(), paginationRequest.getPageSize());

        StoreQuery query = new StoreQuery();
        Map<String, Object> searchParams = paginationRequest.getSearchParams();

        if (searchParams.get("name") != null) {
            query.setName(searchParams.get("name").toString());
        }
        if (searchParams.get("status") != null) {
            query.setStatus(searchParams.get("status").toString());
        }
        if (searchParams.get("merchantId") != null) {
            query.setMerchantId(Integer.parseInt(searchParams.get("merchantId").toString()));
        }
        if (searchParams.get("storeId") != null) {
            query.setStoreId(Integer.parseInt(searchParams.get("storeId").toString()));
        }

        List<Store> storeList = storeRepository.findByParams(query.toParams());
        List<StoreDto> dataList = storeList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        // 填充商户名称
        for (StoreDto storeDto : dataList) {
            MtMerchant mtMerchant = mtMerchantMapper.selectById(storeDto.getMerchantId());
            if (mtMerchant != null) {
                storeDto.setMerchantName(mtMerchant.getName());
            }
            // 隐藏手机号部分信息
            if (storeDto.getPhone() != null) {
                storeDto.setPhone(CommonUtil.hidePhone(storeDto.getPhone()));
            }
        }

        PageRequest pageRequest = PageRequest.of(paginationRequest.getCurrentPage(), paginationRequest.getPageSize());
        PageImpl pageImpl = new PageImpl(dataList, pageRequest, pageHelper.getTotal());
        PaginationResponse<StoreDto> paginationResponse = new PaginationResponse(pageImpl, StoreDto.class);
        paginationResponse.setTotalPages(pageHelper.getPages());
        paginationResponse.setTotalElements(pageHelper.getTotal());
        paginationResponse.setContent(dataList);

        return paginationResponse;
    }

    /**
     * 根据ID查询店铺
     */
    public Store queryById(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        return storeRepository.findById(StoreId.of(id));
    }

    /**
     * 根据ID查询店铺DTO
     */
    public StoreDto queryDtoById(Integer id) {
        Store store = queryById(id);
        return store != null ? toDto(store) : null;
    }

    /**
     * 根据名称查询店铺
     */
    public StoreDto queryByName(String storeName) {
        Store store = storeRepository.findByName(storeName);
        return store != null ? toDto(store) : null;
    }

    /**
     * 获取默认店铺
     */
    public Store getDefaultStore(String merchantNo) {
        return storeRepository.findDefaultByMerchantNo(merchantNo);
    }

    /**
     * 根据参数查询店铺列表
     */
    public List<Store> queryByParams(Map<String, Object> params) {
        return storeRepository.findByParams(params);
    }

    /**
     * 根据距离查询店铺列表
     */
    public List<StoreInfo> queryByDistance(String merchantNo, String keyword, String latitude, String longitude) {
        // 获取商户ID
        Integer merchantId = 0;
        if (merchantNo != null && !merchantNo.isEmpty()) {
            MtMerchant mtMerchant = mtMerchantMapper.selectOne(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<MtMerchant>()
                    .eq(MtMerchant::getMerchantNo, merchantNo)
            );
            if (mtMerchant != null) {
                merchantId = mtMerchant.getId();
            }
        }

        List<Store> storeList = storeRepository.findByDistance(merchantId, keyword, latitude, longitude);
        return storeList.stream()
                .map(this::toStoreInfo)
                .collect(Collectors.toList());
    }

    /**
     * 获取我的店铺列表
     */
    public List<Store> getMyStoreList(Integer merchantId, Integer storeId, String status) {
        return storeRepository.findMyStores(merchantId, storeId, status);
    }

    /**
     * 获取店铺名称
     */
    public String getStoreNames(String storeIds) {
        if (storeIds == null || storeIds.isEmpty()) {
            return "";
        }

        String[] ids = storeIds.split(",");
        List<String> storeNames = new ArrayList<>();

        for (String id : ids) {
            try {
                Store store = storeRepository.findById(StoreId.of(Integer.parseInt(id.trim())));
                if (store != null) {
                    storeNames.add(store.getName());
                }
            } catch (Exception e) {
                // ignore
            }
        }

        return String.join(",", storeNames);
    }

    /**
     * 获取店铺ID
     */
    public String getStoreIds(Integer merchantId, String storeNames) {
        if (storeNames == null || storeNames.isEmpty()) {
            return "";
        }

        String[] names = storeNames.split(",");
        List<String> storeIds = new ArrayList<>();

        for (String name : names) {
            Store store = storeRepository.findByName(name.trim());
            if (store != null) {
                storeIds.add(store.getId().toString());
            }
        }

        return String.join(",", storeIds);
    }

    /**
     * 将领域对象转换为DTO
     */
    private StoreDto toDto(Store store) {
        StoreDto dto = new StoreDto();
        dto.setId(store.getId());
        dto.setMerchantId(store.getMerchantId());
        dto.setName(store.getName());
        dto.setLogo(store.getLogo());
        dto.setQrCode(store.getQrCode());
        dto.setIsDefault(store.getIsDefault());
        dto.setContact(store.getContact());
        dto.setPhone(store.getPhone());
        dto.setAddress(store.getAddress());
        dto.setLatitude(store.getLatitude());
        dto.setLongitude(store.getLongitude());
        dto.setHours(store.getHours());
        dto.setLicense(store.getLicense());
        dto.setCreditCode(store.getCreditCode());

        if (store.getBankAccount() != null) {
            dto.setBankName(store.getBankAccount().getBankName());
            dto.setBankCardName(store.getBankAccount().getBankCardName());
            dto.setBankCardNo(store.getBankAccount().getBankCardNo());
        }

        if (store.getPaymentConfig() != null) {
            dto.setWxMchId(store.getPaymentConfig().getWxMchId());
            dto.setWxApiV2(store.getPaymentConfig().getWxApiV2());
            dto.setWxCertPath(store.getPaymentConfig().getWxCertPath());
            dto.setAlipayAppId(store.getPaymentConfig().getAlipayAppId());
            dto.setAlipayPrivateKey(store.getPaymentConfig().getAlipayPrivateKey());
            dto.setAlipayPublicKey(store.getPaymentConfig().getAlipayPublicKey());
        }

        dto.setDescription(store.getDescription());
        dto.setCreateTime(store.getCreateTime());
        dto.setUpdateTime(store.getUpdateTime());
        dto.setStatus(store.getStatus().getCode());
        dto.setOperator(store.getOperator());

        return dto;
    }

    /**
     * 将领域对象转换为StoreInfo
     */
    private StoreInfo toStoreInfo(Store store) {
        StoreInfo info = new StoreInfo();
        BeanUtils.copyProperties(toDto(store), info);
        info.setDistance(store.getDistance());
        return info;
    }
}
