package cloud.plambiz.module.backendApi.controller;

import cloud.plambiz.common.Constants;
import cloud.plambiz.common.dto.AccountInfo;
import cloud.plambiz.common.service.ActionLogService;
import cloud.plambiz.common.util.TokenUtil;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.TActionLog;
import cloud.plambiz.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台日志管理控制器
 */
@Api(tags="管理端-日志相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/actlog")
public class BackendActionLogController extends BaseController {

    /**
     * 管理员接口
     * */
    private ActionLogService tActionLogService;

    /**
     * 操作日志列表
     */
    @ApiOperation(value = "操作日志列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseObject list(HttpServletRequest request) {
        Integer page = request.getParameter("page") == null ? Constants.PAGE_NUMBER : Integer.parseInt(request.getParameter("page"));
        Integer pageSize = request.getParameter("pageSize") == null ? Constants.PAGE_SIZE : Integer.parseInt(request.getParameter("pageSize"));
        String accountName = request.getParameter("accountName") == null ? "" : request.getParameter("accountName");
        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");
        String ip = request.getParameter("ip") == null ? "" : request.getParameter("ip");
        String beginTime = request.getParameter("params[beginTime]") == null ? "" : request.getParameter("params[beginTime]");
        String endTime = request.getParameter("params[endTime]") == null ? "" : request.getParameter("params[endTime]");
        AccountInfo accountInfo = TokenUtil.getAccountInfo();

        Map<String, Object> searchParams = new HashMap<>();
        if (StringUtil.isNotEmpty(accountName)) {
            searchParams.put("name", accountName);
        }
        if (StringUtil.isNotEmpty(keyword)) {
            searchParams.put("module", keyword);
        }
        if (StringUtil.isNotEmpty(beginTime)) {
            searchParams.put("startTime", beginTime);
        }
        if (StringUtil.isNotEmpty(endTime)) {
            searchParams.put("endTime", endTime);
        }
        if (StringUtil.isNotEmpty(ip)) {
            searchParams.put("ip", ip);
        }
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            searchParams.put("merchantId", accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            searchParams.put("storeId", accountInfo.getStoreId());
        }
        PaginationResponse<TActionLog> paginationResponse = tActionLogService.findLogsByPagination(new PaginationRequest(page, pageSize, searchParams));
        return getSuccessResult(paginationResponse);
    }
}
