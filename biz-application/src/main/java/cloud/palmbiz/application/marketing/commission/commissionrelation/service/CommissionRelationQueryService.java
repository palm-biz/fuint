package cloud.palmbiz.application.marketing.commission.commissionrelation.service;

import cloud.palmbiz.common.commission.dto.CommissionRelationDto;
import cloud.palmbiz.common.commission.dto.CommissionRelationPage;
import cloud.palmbiz.application.marketing.commission.service.CommissionRelationService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommissionRelationQueryService {

    private final CommissionRelationService commissionRelationService;

    public PaginationResponse<CommissionRelationDto> queryRelationByPagination(CommissionRelationPage commissionRelationPage) {
        return commissionRelationService.queryRelationByPagination(commissionRelationPage);
    }
}
