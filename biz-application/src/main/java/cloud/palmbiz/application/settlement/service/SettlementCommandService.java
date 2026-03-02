package cloud.palmbiz.application.settlement.service;

import cloud.palmbiz.common.settlement.request.SettlementRequest;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettlementCommandService {

    private final SettlementService settlementService;

    @Transactional(rollbackFor = Exception.class)
    public Boolean submitSettlement(SettlementRequest requestParam) throws BusinessCheckException {
        return settlementService.submitSettlement(requestParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean doConfirm(Integer settlementId, String operator) throws BusinessCheckException {
        return settlementService.doConfirm(settlementId, operator);
    }
}
