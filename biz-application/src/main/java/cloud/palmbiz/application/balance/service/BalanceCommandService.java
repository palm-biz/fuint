package cloud.palmbiz.application.balance.service;

import cloud.palmbiz.application.account.dto.AccountInfoDto;
import cloud.palmbiz.common.service.BalanceService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceCommandService {

    private final BalanceService balanceService;

    @Transactional(rollbackFor = Exception.class)
    public Boolean addBalance(MtBalance reqDto, Boolean updateBalance) throws BusinessCheckException {
        return balanceService.addBalance(reqDto, updateBalance);
    }

    @Transactional(rollbackFor = Exception.class)
    public void distribute(AccountInfoDto accountInfo, String object, String userIds, String amount, String remark) throws BusinessCheckException {
        balanceService.distribute(accountInfo, object, userIds, amount, remark);
    }
}
