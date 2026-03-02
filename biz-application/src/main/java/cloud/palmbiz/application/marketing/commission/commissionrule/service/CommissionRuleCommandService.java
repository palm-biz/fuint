package cloud.palmbiz.application.marketing.commission.commissionrule.service;

import cloud.palmbiz.common.commission.param.CommissionRuleParam;
import cloud.palmbiz.application.marketing.commission.service.CommissionRuleService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtCommissionRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommissionRuleCommandService {

    private final CommissionRuleService commissionRuleService;

    @Transactional(rollbackFor = Exception.class)
    public MtCommissionRule addCommissionRule(CommissionRuleParam commissionRule) throws BusinessCheckException {
        return commissionRuleService.addCommissionRule(commissionRule);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtCommissionRule updateCommissionRule(CommissionRuleParam commissionRule) throws BusinessCheckException {
        return commissionRuleService.updateCommissionRule(commissionRule);
    }
}
