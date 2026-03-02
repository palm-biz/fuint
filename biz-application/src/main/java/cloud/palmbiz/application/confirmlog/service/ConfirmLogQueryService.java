package cloud.palmbiz.application.confirmlog.service;

import cloud.palmbiz.common.confirmlog.dto.ConfirmLogDto;
import cloud.palmbiz.common.confirmlog.dto.ConfirmLogPage;
import cloud.palmbiz.common.confirmlog.dto.WriteOffRecord;
import cloud.palmbiz.common.service.ConfirmLogService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmLogQueryService {

    private final ConfirmLogService confirmLogService;

    public PaginationResponse<ConfirmLogDto> queryConfirmLogListByPagination(ConfirmLogPage confirmLogPage) {
        return confirmLogService.queryConfirmLogListByPagination(confirmLogPage);
    }

    public Long getConfirmNum(Integer userCouponId) {
        return confirmLogService.getConfirmNum(userCouponId);
    }

    public List<WriteOffRecord> getConfirmList(Integer userCouponId) {
        return confirmLogService.getConfirmList(userCouponId);
    }

    public Long getConfirmCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        return confirmLogService.getConfirmCount(merchantId, storeId, beginTime, endTime);
    }
}
