package cloud.palmbiz.interfaces.client.controller;

import cloud.palmbiz.interfaces.param.MemberInfoParam;
import cloud.palmbiz.common.service.MemberService;
import cloud.palmbiz.common.service.MerchantService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 收银台controller
 */
@Api(tags="会员端-收银台相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/cashier")
public class ClientCashierController extends BaseController {

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 商户服务接口
     */
    private MerchantService merchantService;

    /**
     * 获取会员信息
     */
    @ApiOperation(value = "查询会员信息")
    @RequestMapping(value = "/memberInfo", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject memberInfo(HttpServletRequest request,  @RequestBody MemberInfoParam memberInfoParam) throws BusinessCheckException {
        String merchantNo = request.getHeader("merchantNo") == null ? "" : request.getHeader("merchantNo");
        Integer merchantId = merchantService.getMerchantId(merchantNo);

        String mobile = memberInfoParam.getMobile() == null ? "" : memberInfoParam.getMobile();
        if (StringUtil.isEmpty(mobile)) {
            return getFailureResult(201);
        }

        MtUser userInfo = memberService.queryMemberByMobile(merchantId, mobile);
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("memberInfo", userInfo);

        return getSuccessResult(outParams);
    }
}
