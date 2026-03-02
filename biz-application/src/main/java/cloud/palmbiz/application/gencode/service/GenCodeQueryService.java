package cloud.palmbiz.application.gencode.service;

import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.TGenCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenCodeQueryService {

    private final GenCodeService genCodeService;

    public PaginationResponse<TGenCode> queryGenCodeListByPagination(PaginationRequest paginationRequest) {
        return genCodeService.queryGenCodeListByPagination(paginationRequest);
    }

    public TGenCode queryGenCodeById(Integer id) {
        return genCodeService.queryGenCodeById(id);
    }
}
