package cloud.palmbiz.application.stock.service;

import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtStock;
import cloud.palmbiz.infrastructure.model.MtStockItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockQueryService {

    private final StockService stockService;

    public PaginationResponse<MtStock> queryStockListByPagination(PaginationRequest paginationRequest) {
        return stockService.queryStockListByPagination(paginationRequest);
    }

    public MtStock queryStockById(Long id) {
        return stockService.queryStockById(id);
    }

    public List<MtStockItem> queryItemByParams(Map<String, Object> params) {
        return stockService.queryItemByParams(params);
    }
}
