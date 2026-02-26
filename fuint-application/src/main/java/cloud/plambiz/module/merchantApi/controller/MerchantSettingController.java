package cloud.plambiz.module.merchantApi.controller;

import cloud.plambiz.common.dto.MerchantSettingDto;
import cloud.plambiz.common.dto.StaffDto;
import cloud.plambiz.common.dto.UserInfo;
import cloud.plambiz.common.service.MemberService;
import cloud.plambiz.common.service.MerchantService;
import cloud.plambiz.common.service.SettingService;
import cloud.plambiz.common.service.StaffService;
import cloud.plambiz.common.util.TokenUtil;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.web.BaseController;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.module.merchantApi.request.MerchantSettingParam;
import cloud.plambiz.repository.model.MtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 商户相关controller
 */
@Api(tags="商户端-商户设置相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/merchantApi/merchantSetting")
public class MerchantSettingController extends BaseController {

    /**
     * 会员服务接口
     * */
    private MemberService memberService;

    /**
     * 店铺员工服务接口
     * */
    private StaffService staffService;

    /**
     * 商户服务接口
     * */
    private MerchantService merchantService;

    /**
     * 系统设置服务接口
     * */
    private SettingService settingService;

    /**
     * 查询商户设置信息
     */
    @ApiOperation(value = "查询商户设置信息")
    @RequestMapping(value = "/settingInfo", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject settingInfo() {
        UserInfo userInfo = TokenUtil.getUserInfo();
        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        StaffDto staffInfo = staffService.getStaffInfoByMobile(mtUser.getMobile());
        if (null == staffInfo) {
            return getFailureResult(1002, "您的帐号不是商户，没有操作权限");
        }
        MerchantSettingDto merchantInfo = merchantService.getMerchantSettingInfo(staffInfo.getMerchantId(), staffInfo.getStoreId());
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("imagePath", settingService.getUploadBasePath());
        outParams.put("merchantInfo", merchantInfo);
        return getSuccessResult(outParams);
    }

    /**
     * 保存商户设置
     */
    @ApiOperation(value = "保存商户设置")
    @RequestMapping(value = "/saveSetting", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject saveSetting(@RequestBody MerchantSettingParam params) throws BusinessCheckException {
        UserInfo userInfo = TokenUtil.getUserInfo();
        MtUser mtUser = memberService.queryMemberById(userInfo.getId());
        StaffDto staffInfo = staffService.getStaffInfoByMobile(mtUser.getMobile());
        if (null == staffInfo) {
            return getFailureResult(1002, "您的帐号不是商户，没有操作权限");
        }
        params.setMerchantId(staffInfo.getMerchantId());
        params.setStoreId(staffInfo.getStoreId());
        MerchantSettingDto merchantInfo = merchantService.saveMerchantSetting(params);
        return getSuccessResult(merchantInfo);
    }
}
