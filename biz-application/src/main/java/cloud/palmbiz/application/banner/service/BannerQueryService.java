package cloud.palmbiz.application.banner.service;

import cloud.palmbiz.common.banner.dto.BannerPage;
import cloud.palmbiz.common.service.BannerService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtBanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BannerQueryService {

    private final BannerService bannerService;

    public PaginationResponse<MtBanner> queryBannerListByPagination(BannerPage bannerPage) {
        return bannerService.queryBannerListByPagination(bannerPage);
    }

    public MtBanner queryBannerById(Integer id) {
        return bannerService.queryBannerById(id);
    }

    public List<MtBanner> queryBannerListByParams(Map<String, Object> params) {
        return bannerService.queryBannerListByParams(params);
    }
}
