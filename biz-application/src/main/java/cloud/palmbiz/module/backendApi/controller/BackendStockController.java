package cloud.palmbiz.module.backendApi.controller;

import cloud.palmbiz.common.Constants;
import cloud.palmbiz.common.dto.AccountInfo;
import cloud.palmbiz.common.dto.GoodsDto;
import cloud.palmbiz.common.dto.StockGoodsDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.service.SettingService;
import cloud.palmbiz.common.service.StockService;
import cloud.palmbiz.common.service.StoreService;
import cloud.palmbiz.common.util.CommonUtil;
import cloud.palmbiz.common.util.ListUtil;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.mapper.MtGoodsMapper;
import cloud.palmbiz.infrastructure.mapper.MtGoodsSkuMapper;
import cloud.palmbiz.infrastructure.model.*;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商品库存管理管理controller
 */
@Api(tags="管理端-商品库存管理相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/stock")
public class BackendStockController extends BaseController {

    private MtGoodsMapper mtGoodsMapper;

    private MtGoodsSkuMapper mtGoodsSkuMapper;

    /**
     * 商品分类服务接口
     */
    private StockService stockService;

    /**
     * 配置服务接口
     */
    private SettingService settingService;

    /**
     * 店铺服务接口
     */
    private StoreService storeService;

    /**
     * 获取库存管理记录列表
     */
    @ApiOperation(value = "获取库存管理记录列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('stock:index')")
    public ResponseObject list(HttpServletRequest request) throws BusinessCheckException {
        Integer page = request.getParameter("page") == null ? Constants.PAGE_NUMBER : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("pageSize") == null ? Constants.PAGE_SIZE : Integer.parseInt(request.getParameter("pageSize"));
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String searchStoreId = request.getParameter("storeId");
        String type = request.getParameter("type");

        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        Integer storeId = accountInfo.getStoreId() == null ? 0 : accountInfo.getStoreId();

        Map<String, Object> params = new HashMap<>();
        if (StringUtil.isNotEmpty(description)) {
            params.put("description", description);
        }
        if (StringUtil.isNotEmpty(status)) {
            params.put("status", status);
        }
        if (StringUtil.isNotEmpty(type)) {
            params.put("type", type);
        }
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            params.put("merchantId", accountInfo.getMerchantId());
        }
        if (StringUtil.isNotEmpty(searchStoreId)) {
            params.put("storeId", searchStoreId);
        }
        if (storeId > 0) {
            params.put("storeId", storeId);
        }
        PaginationResponse<MtStock> paginationResponse = stockService.queryStockListByPagination(new PaginationRequest(page, pageSize, params));

        // 店铺列表
        List<MtStore> storeList = storeService.getMyStoreList(accountInfo.getMerchantId(), accountInfo.getStoreId(), StatusEnum.ENABLED.getKey());

        Map<String, Object> result = new HashMap<>();
        result.put("imagePath", settingService.getUploadBasePath());
        result.put("storeList", storeList);
        result.put("paginationResponse", paginationResponse);

        return getSuccessResult(result);
    }

    /**
     * 删除库存管理记录状态
     */
    @ApiOperation(value = "删除库存管理记录状态")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('stock:index')")
    public ResponseObject delete(@RequestBody Map<String, Object> params) throws BusinessCheckException {
        Integer id = params.get("id") == null ? 0 : Integer.parseInt(params.get("id").toString());

        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        MtStock mtStock = stockService.queryStockById(id.longValue());
        if (mtStock == null) {
            return getFailureResult(201, "该数据不存在");
        }

        stockService.delete(id, accountInfo.getAccountName());
        return getSuccessResult(true);
    }

    /**
     * 保存库存管理记录
     */
    @ApiOperation(value = "保存库存管理记录")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('stock:index')")
    public ResponseObject save(@RequestBody Map<String, Object> params) throws BusinessCheckException {
        String type = params.get("type") == null ? "" : CommonUtil.replaceXSS(params.get("type").toString());
        String description = params.get("description") == null ? "" : CommonUtil.replaceXSS(params.get("description").toString());
        String status = params.get("status") == null ? StatusEnum.ENABLED.getKey() : params.get("status").toString();
        Integer storeId = (params.get("storeId") == null || StringUtil.isEmpty(params.get("storeId").toString())) ? 0 : Integer.parseInt(params.get("storeId").toString());
        List<LinkedHashMap<String, Object>> originalMapList = (List<LinkedHashMap<String, Object>>) params.get("goodsList");
        List<StockGoodsDto> goodsList = ListUtil.convertMapListToDtoList(originalMapList, StockGoodsDto.class);

        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        Integer myStoreId = accountInfo.getStoreId();
        if (myStoreId != null && myStoreId > 0) {
            storeId = myStoreId;
        }

        MtStock mtStock = new MtStock();
        mtStock.setMerchantId(accountInfo.getMerchantId());
        mtStock.setDescription(description);
        mtStock.setStatus(status);
        mtStock.setStoreId(storeId);
        mtStock.setType(type);
        String operator = accountInfo.getAccountName();
        mtStock.setOperator(operator);
        stockService.addStock(mtStock, goodsList);

        return getSuccessResult(true);
    }

    /**
     * 获取库存管理记录详情
     */
    @ApiOperation(value = "获取库存管理记录详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('stock:index')")
    public ResponseObject info(@PathVariable("id") Integer id) throws BusinessCheckException {
        MtStock mtStock = stockService.queryStockById(id.longValue());
        Map<String, Object> param = new HashMap<>();
        param.put("STOCK_ID", mtStock.getId());
        List<MtStockItem> stockItems = stockService.queryItemByParams(param);
        List<GoodsDto> goodsList = new ArrayList<>();
        if (stockItems != null && stockItems.size() > 0) {
            for (MtStockItem stockItem : stockItems) {
                 MtGoods mtGoods = mtGoodsMapper.selectById(stockItem.getGoodsId());
                 GoodsDto goodsDto = new GoodsDto();
                 goodsDto.setGoodsNo(mtGoods.getGoodsNo());
                 goodsDto.setName(mtGoods.getName());
                 goodsDto.setLogo(mtGoods.getLogo());
                 goodsDto.setNum(stockItem.getNum());
                 goodsDto.setStatus(mtGoods.getStatus());
                 if (stockItem.getSkuId() != null && stockItem.getSkuId() > 0) {
                     MtGoodsSku mtGoodsSku = mtGoodsSkuMapper.selectById(stockItem.getSkuId());
                     if (mtGoodsSku != null) {
                         goodsDto.setGoodsNo(mtGoodsSku.getSkuNo());
                         if (StringUtil.isNotEmpty(mtGoodsSku.getLogo())) {
                             goodsDto.setLogo(mtGoodsSku.getLogo());
                         }
                     }
                 }
                 goodsList.add(goodsDto);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("stockInfo", mtStock);
        result.put("goodsList", goodsList);

        return getSuccessResult(result);
    }
}
