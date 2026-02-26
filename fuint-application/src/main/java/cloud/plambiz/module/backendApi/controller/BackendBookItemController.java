package cloud.plambiz.module.backendApi.controller;

import cloud.plambiz.common.dto.AccountInfo;
import cloud.plambiz.common.dto.BookItemDto;
import cloud.plambiz.common.dto.ParamDto;
import cloud.plambiz.common.enums.BookStatusEnum;
import cloud.plambiz.common.enums.StatusEnum;
import cloud.plambiz.common.param.BookItemPage;
import cloud.plambiz.common.param.StatusParam;
import cloud.plambiz.common.service.BookCateService;
import cloud.plambiz.common.service.BookItemService;
import cloud.plambiz.common.service.SettingService;
import cloud.plambiz.common.service.StoreService;
import cloud.plambiz.common.util.TokenUtil;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.MtBookCate;
import cloud.plambiz.repository.model.MtBookItem;
import cloud.plambiz.repository.model.MtStore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约订单管理类controller
 */
@Api(tags="管理端-预约订单相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/bookItem")
public class BackendBookItemController extends BaseController {

    /**
     * 预约订单服务接口
     */
    private BookItemService bookItemService;

    /**
     * 系统设置服务接口
     * */
    private SettingService settingService;

    /**
     * 店铺服务接口
     */
    private StoreService storeService;

    /**
     * 预约分类服务接口
     */
    private BookCateService bookCateService;

    /**
     * 预约订单列表查询
     */
    @ApiOperation(value = "预约订单列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('book:index')")
    public ResponseObject list(@ModelAttribute BookItemPage bookItemPage) throws BusinessCheckException {
        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            bookItemPage.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            bookItemPage.setStoreId(accountInfo.getStoreId());
        }

        PaginationResponse<BookItemDto> paginationResponse = bookItemService.queryBookItemListByPagination(bookItemPage);
        List<MtStore> storeList = storeService.getMyStoreList(accountInfo.getMerchantId(), accountInfo.getStoreId(), StatusEnum.ENABLED.getKey());
        List<ParamDto> bookStatusList = BookStatusEnum.getBookStatusList(BookStatusEnum.DELETE.getKey());
        List<MtBookCate> cateList = bookCateService.getAvailableBookCate(accountInfo.getMerchantId(), accountInfo.getStoreId());

        Map<String, Object> result = new HashMap<>();
        result.put("dataList", paginationResponse);
        result.put("imagePath", settingService.getUploadBasePath());
        result.put("storeList", storeList);
        result.put("bookStatusList", bookStatusList);
        result.put("cateList", cateList);

        return getSuccessResult(result);
    }

    /**
     * 更新预约订单状态
     */
    @ApiOperation(value = "更新预约订单状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('book:index')")
    public ResponseObject updateStatus(@RequestBody StatusParam params) throws BusinessCheckException {
        String status = params.getStatus() != null ? params.getStatus() : StatusEnum.ENABLED.getKey();
        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        MtBookItem mtBookItem = bookItemService.getBookItemById(params.getId());
        if (mtBookItem == null) {
            return getFailureResult(201, "该数据不存在");
        }

        mtBookItem.setOperator(accountInfo.getAccountName());
        mtBookItem.setStatus(status);
        bookItemService.updateBookItem(mtBookItem);

        return getSuccessResult(true);
    }

    /**
     * 保存预约订单
     */
    @ApiOperation(value = "保存预约订单")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('book:index')")
    public ResponseObject saveHandler(@RequestBody BookItemDto bookItemDto) throws BusinessCheckException, ParseException {
        AccountInfo accountInfo = TokenUtil.getAccountInfo();

        MtBookItem mtBookItem = new MtBookItem();
        BeanUtils.copyProperties(bookItemDto, mtBookItem);
        mtBookItem.setOperator(accountInfo.getAccountName());
        mtBookItem.setMerchantId(accountInfo.getMerchantId());
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            mtBookItem.setStoreId(accountInfo.getStoreId());
        }
        if (bookItemDto.getId() != null && mtBookItem.getId() > 0) {
            bookItemService.updateBookItem(mtBookItem);
        } else {
            bookItemService.addBookItem(mtBookItem);
        }

        return getSuccessResult(true);
    }

    /**
     * 获取预约订单详情
     */
    @ApiOperation(value = "获取预约订单详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('book:index')")
    public ResponseObject info(@PathVariable("id") Integer id) throws BusinessCheckException {
        AccountInfo accountInfo = TokenUtil.getAccountInfo();
        MtBookItem mtBookItem = bookItemService.getBookItemById(id);
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0 && !mtBookItem.getMerchantId().equals(accountInfo.getMerchantId())) {
            return getFailureResult(1004);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("mtBookItem", mtBookItem);
        return getSuccessResult(result);
    }
}
