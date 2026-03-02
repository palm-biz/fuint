package cloud.palmbiz.application.marketing.commission.commissionlog.service;

import cloud.palmbiz.common.commission.dto.CommissionLogRequest;
import cloud.palmbiz.application.marketing.commission.service.CommissionLogService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommissionLogCommandService {

    private final CommissionLogService commissionLogService;

    @Transactional(rollbackFor = Exception.class)
    public void calculateCommission(Integer orderId) {
        commissionLogService.calculateCommission(orderId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCommissionLog(CommissionLogRequest requestParam) throws BusinessCheckException {
        commissionLogService.updateCommissionLog(requestParam);
    }
}
