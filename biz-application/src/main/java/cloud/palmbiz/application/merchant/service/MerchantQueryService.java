package cloud.palmbiz.application.merchant.service;

import cloud.palmbiz.application.merchant.query.MerchantQuery;
import cloud.palmbiz.common.merchant.dto.MerchantDto;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.domain.merchant.model.Merchant;
import cloud.palmbiz.domain.merchant.model.MerchantId;
import cloud.palmbiz.domain.merchant.repository.MerchantRepository;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商户查询服务
 */
@Service
@RequiredArgsConstructor
public class MerchantQueryService {

    private final MerchantRepository merchantRepository;

    /**
     * 分页查询商户列表
     */
    public PaginationResponse<MerchantDto> queryByPage(PaginationRequest paginationRequest) {
        Page<Merchant> pageHelper = PageHelper.startPage(paginationRequest.getCurrentPage(), paginationRequest.getPageSize());

        MerchantQuery query = new MerchantQuery();
        Map<String, Object> searchParams = paginationRequest.getSearchParams();

        if (searchParams.get("name") != null) {
            query.setName(searchParams.get("name").toString());
        }
        if (searchParams.get("status") != null) {
            query.setStatus(searchParams.get("status").toString());
        }
        if (searchParams.get("id") != null) {
            query.setId(Integer.parseInt(searchParams.get("id").toString()));
        }

        List<Merchant> merchantList = merchantRepository.findByParams(query.toParams());
        List<MerchantDto> dataList = merchantList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        // 隐藏手机号部分信息
        for (MerchantDto dto : dataList) {
            if (dto.getPhone() != null) {
                dto.setPhone(CommonUtil.hidePhone(dto.getPhone()));
            }
        }

        PageRequest pageRequest = PageRequest.of(paginationRequest.getCurrentPage(), paginationRequest.getPageSize());
        PageImpl pageImpl = new PageImpl(dataList, pageRequest, pageHelper.getTotal());
        PaginationResponse<MerchantDto> paginationResponse = new PaginationResponse(pageImpl, MerchantDto.class);
        paginationResponse.setTotalPages(pageHelper.getPages());
        paginationResponse.setTotalElements(pageHelper.getTotal());
        paginationResponse.setContent(dataList);

        return paginationResponse;
    }

    /**
     * 根据ID查询商户
     */
    public Merchant queryById(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        return merchantRepository.findById(MerchantId.of(id));
    }

    /**
     * 根据ID查询商户DTO
     */
    public MerchantDto queryDtoById(Integer id) {
        Merchant merchant = queryById(id);
        return merchant != null ? toDto(merchant) : null;
    }

    /**
     * 根据名称查询商户
     */
    public Merchant queryByName(String name) {
        return merchantRepository.findByName(name);
    }

    /**
     * 根据商户号查询商户
     */
    public Merchant queryByNo(String merchantNo) {
        return merchantRepository.findByNo(merchantNo);
    }

    /**
     * 根据商户号获取商户ID
     */
    public Integer getMerchantId(String merchantNo) {
        Merchant merchant = queryByNo(merchantNo);
        return merchant != null ? merchant.getId() : null;
    }

    /**
     * 根据参数查询商户列表
     */
    public List<Merchant> queryByParams(Map<String, Object> params) {
        return merchantRepository.findByParams(params);
    }

    /**
     * 获取我的商户列表
     */
    public List<Merchant> getMyMerchantList(Integer merchantId, Integer storeId, String status) {
        return merchantRepository.findMyMerchants(merchantId, storeId, status);
    }

    /**
     * 将领域对象转换为DTO
     */
    private MerchantDto toDto(Merchant merchant) {
        MerchantDto dto = new MerchantDto();
        dto.setId(merchant.getId());
        dto.setNo(merchant.getNo());
        dto.setName(merchant.getName());
        dto.setType(merchant.getType());
        dto.setLogo(merchant.getLogo());
        dto.setContact(merchant.getContact());
        dto.setPhone(merchant.getPhone());
        dto.setAddress(merchant.getAddress());
        dto.setWxAppId(merchant.getWxAppId());
        dto.setWxAppSecret(merchant.getWxAppSecret());
        dto.setWxOfficialAppId(merchant.getWxOfficialAppId());
        dto.setWxOfficialAppSecret(merchant.getWxOfficialAppSecret());
        dto.setSettleRate(merchant.getSettleRateValue());
        dto.setDescription(merchant.getDescription());
        dto.setCreateTime(merchant.getCreateTime());
        dto.setUpdateTime(merchant.getUpdateTime());
        dto.setStatus(merchant.getStatus().getCode());
        dto.setOperator(merchant.getOperator());
        return dto;
    }
}
