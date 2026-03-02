package cloud.palmbiz.application.cate.service;

import cloud.palmbiz.common.cate.dto.GoodsCatePage;
import cloud.palmbiz.common.cate.dto.GoodsCateDto;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtGoodsCate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CateQueryService {

    private final CateService cateService;

    public PaginationResponse<GoodsCateDto> queryCateListByPagination(GoodsCatePage catePage) {
        return cateService.queryCateListByPagination(catePage);
    }

    public MtGoodsCate queryCateById(Integer id) {
        return cateService.queryCateById(id);
    }

    public List<MtGoodsCate> getCateList(Integer merchantId, Integer storeId, String name, String status) {
        return cateService.getCateList(merchantId, storeId, name, status);
    }

    public Integer getGoodsCateId(Integer merchantId, Integer storeId, String name) {
        return cateService.getGoodsCateId(merchantId, storeId, name);
    }
}
