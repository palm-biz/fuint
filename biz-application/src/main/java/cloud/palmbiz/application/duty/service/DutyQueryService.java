package cloud.palmbiz.application.duty.service;

import cloud.palmbiz.common.domain.TreeNode;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.TDuty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DutyQueryService {

    private final DutyService dutyService;

    public List<TDuty> getAvailableRoles(Integer merchantId, Integer accountId) {
        return dutyService.getAvailableRoles(merchantId, accountId);
    }

    public TDuty getRoleById(Long roleId) {
        return dutyService.getRoleById(roleId);
    }

    public PaginationResponse<TDuty> findDutiesByPagination(PaginationRequest paginationRequest) {
        return dutyService.findDutiesByPagination(paginationRequest);
    }

    public List<TDuty> findDatasByIds(String[] ids) {
        return dutyService.findDatasByIds(ids);
    }

    public TDuty findByName(Integer merchantId, String name) {
        return dutyService.findByName(merchantId, name);
    }

    public List<Long> getSourceIdsByDutyId(Integer dutyId) {
        return dutyService.getSourceIdsByDutyId(dutyId);
    }

    public List<TreeNode> getDutyTree(Integer merchantId) {
        return dutyService.getDutyTree(merchantId);
    }

    public List<Long> findDutiesByAccountId(Integer accountId) {
        return dutyService.findDutiesByAccountId(accountId);
    }
}
