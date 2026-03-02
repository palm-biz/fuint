package cloud.palmbiz.application.identity.user.userbalance.service;

import cloud.palmbiz.application.identity.user.service.UserBalanceService;
import cloud.palmbiz.common.userbalance.dto.UserBalancePage;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtUserBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBalanceQueryService {

    private final UserBalanceService userBalanceService;

    public PaginationResponse<MtUserBalance> queryUserBalanceListByPagination(UserBalancePage userBalancePage) {
        return userBalanceService.queryUserBalanceListByPagination(userBalancePage);
    }

    public MtUserBalance queryUserBalanceById(Integer id) {
        return userBalanceService.queryUserBalanceById(id);
    }
}
