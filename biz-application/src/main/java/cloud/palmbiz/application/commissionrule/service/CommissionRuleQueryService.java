package cloud.palmbiz.application.commissionrule.service;

import cloud.palmbiz.common.commission.dto.CommissionRuleDto;
import cloud.palmbiz.common.commission.dto.CommissionRulePage;
import cloud.palmbiz.common.service.CommissionRuleService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtCommissionRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommissionRuleQueryService {

    private final CommissionRuleService commissionRuleService;

    public PaginationResponse<MtCommissionRule> queryDataByPagination(CommissionRulePage commissionRulePage) {
        return commissionRuleService.queryDataByPagination(commissionRulePage);
    }

    public CommissionRuleDto queryCommissionRuleById(Integer id) {
        return commissionRuleService.queryCommissionRuleById(id);
    }
}
