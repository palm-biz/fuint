package cloud.palmbiz.application.marketing.commission.commissionlog.service;

import cloud.palmbiz.common.commission.dto.CommissionLogDto;
import cloud.palmbiz.common.commission.dto.CommissionLogPage;
import cloud.palmbiz.application.marketing.commission.service.CommissionLogService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommissionLogQueryService {

    private final CommissionLogService commissionLogService;

    public PaginationResponse<CommissionLogDto> queryCommissionLogByPagination(CommissionLogPage commissionLogPage) {
        return commissionLogService.queryCommissionLogByPagination(commissionLogPage);
    }

    public CommissionLogDto queryCommissionLogById(Integer id) {
        return commissionLogService.queryCommissionLogById(id);
    }
}
