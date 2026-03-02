package cloud.palmbiz.application.give.service;

import cloud.palmbiz.common.give.dto.GiveDto;
import cloud.palmbiz.common.service.GiveService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtGive;
import cloud.palmbiz.infrastructure.model.MtGiveItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GiveQueryService {

    private final GiveService giveService;

    public PaginationResponse<GiveDto> queryGiveListByPagination(PaginationRequest paginationRequest) {
        return giveService.queryGiveListByPagination(paginationRequest);
    }

    public MtGive queryGiveById(Long id) {
        return giveService.queryGiveById(id);
    }

    public List<MtGiveItem> queryItemByParams(Map<String, Object> params) {
        return giveService.queryItemByParams(params);
    }
}
