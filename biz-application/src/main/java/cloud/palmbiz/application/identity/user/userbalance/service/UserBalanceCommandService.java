package cloud.palmbiz.application.identity.user.userbalance.service;

import cloud.palmbiz.application.identity.user.service.UserBalanceService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtUserBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserBalanceCommandService {

    private final UserBalanceService userBalanceService;

    @Transactional(rollbackFor = Exception.class)
    public MtUserBalance addUserBalance(MtUserBalance mtUserBalance) throws BusinessCheckException {
        return userBalanceService.addUserBalance(mtUserBalance);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtUserBalance updateUserBalance(MtUserBalance mtUserBalance) throws BusinessCheckException {
        return userBalanceService.updateUserBalance(mtUserBalance);
    }
}
