package cloud.palmbiz.module.client.controller;

import cloud.palmbiz.common.user.dto.UserInfoDto;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 帮助相关controller
 */
@Api(tags="会员端-帮助相关接口")
@RestController
@RequestMapping(value = "/client/help")
public class ClientHelpController extends BaseController {

    /**
     * 查询帮助内容列表
     */
    @ApiOperation(value = "查询帮助列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject list() throws BusinessCheckException {
        UserInfoDto mtUser = TokenUtil.getUserInfo();
        if (null == mtUser) {
            return getFailureResult(1001);
        }
        ArrayList<String> data = new ArrayList<>();
        return getSuccessResult(data);
    }
}
