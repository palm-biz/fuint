package cloud.palmbiz.application.stock.service;

import cloud.palmbiz.common.stock.dto.StockGoodsDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockCommandService {

    private final StockService stockService;

    @Transactional(rollbackFor = Exception.class)
    public void addStock(MtStock mtStock, List<StockGoodsDto> goodsList) throws BusinessCheckException {
        stockService.addStock(mtStock, goodsList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id, String operator) {
        stockService.delete(id, operator);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean addStockRecord(Integer merchantId, Integer storeId, Integer goodsId, Integer skuId, String type, Double num, String description) {
        return stockService.addStockRecord(merchantId, storeId, goodsId, skuId, type, num, description);
    }
}
