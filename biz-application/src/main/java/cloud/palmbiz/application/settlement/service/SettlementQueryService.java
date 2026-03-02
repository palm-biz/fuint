package cloud.palmbiz.application.settlement.service;

import cloud.palmbiz.common.settlement.dto.SettlementDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtSettlement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementQueryService {

    private final SettlementService settlementService;

    public PaginationResponse<MtSettlement> querySettlementListByPagination(PaginationRequest paginationRequest) {
        return settlementService.querySettlementListByPagination(paginationRequest);
    }

    public SettlementDto getSettlementInfo(Integer settlementId, Integer page, Integer pageSize) throws BusinessCheckException {
        return settlementService.getSettlementInfo(settlementId, page, pageSize);
    }
}
