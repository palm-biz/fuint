package cloud.palmbiz.application.point.service;

import cloud.palmbiz.common.point.dto.PointDto;
import cloud.palmbiz.common.service.PointService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointQueryService {

    private final PointService pointService;

    public PaginationResponse<PointDto> queryPointListByPagination(PaginationRequest paginationRequest) {
        return pointService.queryPointListByPagination(paginationRequest);
    }
}
