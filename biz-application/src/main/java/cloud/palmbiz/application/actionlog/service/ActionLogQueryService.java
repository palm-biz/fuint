package cloud.palmbiz.application.actionlog.service;

import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.TActionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionLogQueryService {

    private final ActionLogService actionLogService;

    public PaginationResponse<TActionLog> findLogsByPagination(PaginationRequest paginationRequest) {
        return actionLogService.findLogsByPagination(paginationRequest);
    }
}
