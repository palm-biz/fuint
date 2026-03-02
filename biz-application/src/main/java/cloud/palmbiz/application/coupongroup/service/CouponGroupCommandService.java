package cloud.palmbiz.application.coupongroup.service;

import cloud.palmbiz.common.coupon.dto.ReqCouponGroupDto;
import cloud.palmbiz.common.service.CouponGroupService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtCouponGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CouponGroupCommandService {

    private final CouponGroupService couponGroupService;

    @Transactional(rollbackFor = Exception.class)
    public MtCouponGroup addCouponGroup(ReqCouponGroupDto reqCouponGroupDto) {
        return couponGroupService.addCouponGroup(reqCouponGroupDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtCouponGroup updateCouponGroup(ReqCouponGroupDto reqCouponGroupDto) throws BusinessCheckException {
        return couponGroupService.updateCouponGroup(reqCouponGroupDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCouponGroup(Integer id, String operator) {
        couponGroupService.deleteCouponGroup(id, operator);
    }

    @Transactional(rollbackFor = Exception.class)
    public String importSendCoupon(MultipartFile file, String operator, String filePath) throws BusinessCheckException {
        return couponGroupService.importSendCoupon(file, operator, filePath);
    }
}
