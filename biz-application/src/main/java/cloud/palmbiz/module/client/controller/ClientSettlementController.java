package cloud.palmbiz.module.client.controller;

import cloud.palmbiz.common.param.SettlementParam;
import cloud.palmbiz.common.service.OrderService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 结算中心接口
 */
@Api(tags="会员端-订单结算相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/settlement")
public class ClientSettlementController extends BaseController {

    /**
     * 订单接口
     */
    private OrderService orderService;

    /**
     * 订单结算
     */
    @ApiOperation(value = "提交订单结算")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject submit(HttpServletRequest request, @RequestBody SettlementParam param) throws BusinessCheckException {
        Map<String, Object> result = orderService.doSettle(request, param);
        return getSuccessResult(result);
    }
}
