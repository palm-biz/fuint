package cloud.palmbiz.application.marketing.commission.commissioncash.service;

import cloud.palmbiz.common.commission.dto.CommissionCashDto;
import cloud.palmbiz.common.commission.dto.CommissionCashPage;
import cloud.palmbiz.application.marketing.commission.service.CommissionCashService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommissionCashQueryService {

    private final CommissionCashService commissionCashService;

    public PaginationResponse<CommissionCashDto> queryCommissionCashByPagination(CommissionCashPage commissionCashPage) {
        return commissionCashService.queryCommissionCashByPagination(commissionCashPage);
    }

    public CommissionCashDto queryCommissionCashById(Integer id) throws BusinessCheckException {
        return commissionCashService.queryCommissionCashById(id);
    }
}
