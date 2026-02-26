package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.StockGoodsDto;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.MtStock;
import cloud.plambiz.repository.model.MtStockItem;
import java.util.List;
import java.util.Map;

/**
 * 库存业务接口
 */
public interface StockService extends IService<MtStock> {

    /**
     * 分页查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<MtStock> queryStockListByPagination(PaginationRequest paginationRequest);

    /**
     * 新增库存管理记录
     *
     * @param mtStock
     * @param goodsList
     * @throws BusinessCheckException
     * @return
     */
    ResponseObject addStock(MtStock mtStock, List<StockGoodsDto> goodsList) throws BusinessCheckException;

    /**
     * 删除库存管理记录
     *
     * @param id
     * @param operator
     * @return
     * */
    void delete(Integer id, String operator);

    /**
     * 根据ID获取信息
     *
     * @param  id ID
     * @return
     */
    MtStock queryStockById(Long id);

    /**
     * 根据条件搜索详情
     *
     * @param  params
     * @return
     * */
    List<MtStockItem> queryItemByParams(Map<String, Object> params);

    /**
     * 生成出入库记录
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @param goodsId 商品ID
     * @param skuId 商品SKU ID
     * @param type 类型，increase:入库，reduce:出库
     * @param num 数量
     * @param description 说明
     * @return
     * */
    Boolean addStockRecord(Integer merchantId, Integer storeId, Integer goodsId, Integer skuId, String type, Double num, String description);
}
