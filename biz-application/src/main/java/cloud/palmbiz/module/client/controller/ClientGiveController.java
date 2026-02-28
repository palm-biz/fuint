package cloud.palmbiz.module.client.controller;

import cloud.palmbiz.common.Constants;
import cloud.palmbiz.common.give.dto.GiveDto;
import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.param.GiveListParam;
import cloud.palmbiz.common.param.GiveParam;
import cloud.palmbiz.common.service.GiveService;
import cloud.palmbiz.common.service.MemberService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 卡券转赠controller
 */
@Api(tags="会员端-卡券转赠相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/client/give")
public class ClientGiveController extends BaseController {

    /**
     * 转赠服务接口
     */
    private GiveService giveService;

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 转赠卡券
     */
    @ApiOperation(value = "转赠卡券")
    @RequestMapping(value = "/doGive", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject doGive(@RequestBody GiveParam giveParam) throws BusinessCheckException {
        UserInfoDto userInfo = TokenUtil.getUserInfo();
        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        giveParam.setUserId(mtUser.getId());
        giveParam.setStoreId(mtUser.getStoreId());
        giveParam.setMerchantId(mtUser.getMerchantId());
        ResponseObject result = giveService.addGive(giveParam);
        return getSuccessResult(result.getData());
    }

    /**
     * 查询转赠记录
     */
    @ApiOperation(value = "查询转赠记录")
    @RequestMapping(value = "/giveLog", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject giveLog(@RequestBody GiveListParam giveListParam) {
        UserInfoDto mtUser = TokenUtil.getUserInfo();
        String mobile = giveListParam.getMobile() == null ? "" : giveListParam.getMobile();
        String type = giveListParam.getType() == null ? "give" : giveListParam.getType();
        Integer page = giveListParam.getPage() == null ? Constants.PAGE_NUMBER : giveListParam.getPage();
        Integer pageSize = giveListParam.getPageSize() == null ? Constants.PAGE_SIZE : giveListParam.getPageSize();

        Map<String, Object> searchParams = new HashMap<>();
        if (type.equals("gived")) {
            searchParams.put("userId", mtUser.getId());
        } else {
            searchParams.put("giveUserId", mtUser.getId());
        }

        if (StringUtil.isNotEmpty(mobile) && type.equals("give")) {
            searchParams.put("mobile", mobile);
        } else if(StringUtil.isNotEmpty(mobile) && type.equals("gived")) {
            searchParams.put("userMobile", mobile);
        }
        PaginationResponse<GiveDto> paginationResponse = giveService.queryGiveListByPagination(new PaginationRequest(page, pageSize, searchParams));

        Map<String, Object> outParams = new HashMap();
        outParams.put("content", paginationResponse.getContent());
        outParams.put("pageSize", paginationResponse.getPageSize());
        outParams.put("pageNumber", paginationResponse.getCurrentPage());
        outParams.put("totalRow", paginationResponse.getTotalElements());
        outParams.put("totalPage", paginationResponse.getTotalPages());

        return getSuccessResult(outParams);
    }
}

