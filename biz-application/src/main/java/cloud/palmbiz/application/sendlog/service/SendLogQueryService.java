package cloud.palmbiz.application.sendlog.service;

import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtSendLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendLogQueryService {

    private final SendLogService sendLogService;

    public PaginationResponse<MtSendLog> querySendLogListByPagination(PaginationRequest paginationRequest) {
        return sendLogService.querySendLogListByPagination(paginationRequest);
    }

    public MtSendLog querySendLogById(Long id) {
        return sendLogService.querySendLogById(id);
    }
}
