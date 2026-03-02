package cloud.palmbiz.application.banner.service;

import cloud.palmbiz.common.banner.dto.BannerDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtBanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BannerCommandService {

    private final BannerService bannerService;

    @Transactional(rollbackFor = Exception.class)
    public MtBanner addBanner(BannerDto reqBannerDto) throws BusinessCheckException {
        return bannerService.addBanner(reqBannerDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtBanner updateBanner(BannerDto bannerDto) throws BusinessCheckException {
        return bannerService.updateBanner(bannerDto);
    }
}
