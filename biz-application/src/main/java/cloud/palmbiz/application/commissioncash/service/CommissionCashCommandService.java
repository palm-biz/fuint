package cloud.palmbiz.application.commissioncash.service;

import cloud.palmbiz.common.commission.dto.CommissionCashRequest;
import cloud.palmbiz.common.commission.dto.CommissionSettleConfirmRequest;
import cloud.palmbiz.common.commission.dto.CommissionSettleRequest;
import cloud.palmbiz.common.service.CommissionCashService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommissionCashCommandService {

    private final CommissionCashService commissionCashService;

    @Transactional(rollbackFor = Exception.class)
    public String settleCommission(CommissionSettleRequest commissionSettleRequest) throws BusinessCheckException {
        return commissionCashService.settleCommission(commissionSettleRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCommissionCash(CommissionCashRequest commissionCashRequest) throws BusinessCheckException {
        commissionCashService.updateCommissionCash(commissionCashRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmCommissionCash(CommissionSettleConfirmRequest requestParam) throws BusinessCheckException {
        commissionCashService.confirmCommissionCash(requestParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelCommissionCash(CommissionSettleConfirmRequest requestParam) throws BusinessCheckException {
        commissionCashService.cancelCommissionCash(requestParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public void payToBalance(CommissionCashRequest commissionCashRequest) throws BusinessCheckException {
        commissionCashService.payToBalance(commissionCashRequest);
    }
}
