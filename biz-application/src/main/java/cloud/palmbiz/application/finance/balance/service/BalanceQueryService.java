package cloud.palmbiz.application.finance.balance.service;

import cloud.palmbiz.application.finance.balance.dto.BalanceDto;
import cloud.palmbiz.common.balance.dto.BalancePage;
import cloud.palmbiz.application.banner.service.BalanceService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceQueryService {

    private final BalanceService balanceService;

    public PaginationResponse<BalanceDto> queryBalanceListByPagination(BalancePage balancePage) {
        return balanceService.queryBalanceListByPagination(balancePage);
    }

    public List<MtBalance> getBalanceListByOrderSn(String orderSn) {
        return balanceService.getBalanceListByOrderSn(orderSn);
    }
}
