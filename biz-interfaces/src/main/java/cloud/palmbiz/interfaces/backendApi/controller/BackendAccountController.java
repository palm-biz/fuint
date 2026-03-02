package cloud.palmbiz.interfaces.backendApi.controller;

import cloud.palmbiz.application.account.command.CreateAccountCommand;
import cloud.palmbiz.application.account.command.DeleteAccountCommand;
import cloud.palmbiz.application.account.command.UpdateAccountCommand;
import cloud.palmbiz.application.account.service.AccountCommandService;
import cloud.palmbiz.application.account.service.AccountQueryService;
import cloud.palmbiz.common.account.dto.AccountDto;
import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.role.dto.RoleDto;
import cloud.palmbiz.common.service.DutyService;
import cloud.palmbiz.common.service.MerchantService;
import cloud.palmbiz.common.service.StoreService;
import cloud.palmbiz.common.utils.StringUtil;
import cloud.palmbiz.common.utils.CommonUtil;
import cloud.palmbiz.common.utils.TokenUtil;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtMerchant;
import cloud.palmbiz.infrastructure.model.MtStore;
import cloud.palmbiz.infrastructure.model.TDuty;
import cloud.palmbiz.interfaces.param.AccountPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理员管理
 */
@Api(tags = "管理端-管理员相关接口")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/backendApi/account")
public class BackendAccountController extends BaseController {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;
    private final DutyService tDutyService;
    private final StoreService storeService;
    private final MerchantService merchantService;

    /**
     * 账户信息列表
     */
    @ApiOperation(value = "账户信息列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('system:account:index')")
    public ResponseObject list(@ModelAttribute AccountPage accountPage) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        if (accountInfo.getMerchantId() != null && accountInfo.getMerchantId() > 0) {
            accountPage.setMerchantId(accountInfo.getMerchantId());
        }
        if (accountInfo.getStoreId() != null && accountInfo.getStoreId() > 0) {
            accountPage.setStoreId(accountInfo.getStoreId());
        }
        PaginationResponse<AccountDto> paginationResponse = accountQueryService.queryByPage(accountPage);
        return getSuccessResult(paginationResponse);
    }

    /**
     * 获取账户详情
     */
    @ApiOperation(value = "获取账户详情")
    @RequestMapping(value = "/info/{userId}", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject info(@PathVariable("userId") Long userId) throws BusinessCheckException {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        Map<String, Object> result = new HashMap<>();

        List<TDuty> roleList = tDutyService.getAvailableRoles(accountInfo.getMerchantId(), accountInfo.getId());
        List<RoleDto> roles = new ArrayList<>();
        for (TDuty duty : roleList) {
            RoleDto role = new RoleDto();
            role.setId(duty.getDutyId().longValue());
            role.setName(duty.getDutyName());
            role.setStatus(duty.getStatus());
            roles.add(role);
        }
        result.put("roles", roles);

        List<MtStore> stores = storeService.getMyStoreList(accountInfo.getMerchantId(), accountInfo.getStoreId(), StatusEnum.ENABLED.getKey());
        result.put("stores", stores);

        List<MtMerchant> merchants = merchantService.getMyMerchantList(accountInfo.getMerchantId(), accountInfo.getStoreId(), StatusEnum.ENABLED.getKey());
        result.put("merchants", merchants);

        AccountDto accountDto = null;
        if (userId > 0) {
            AccountInfoDto accountDetail = accountQueryService.queryById(userId.intValue());
            if (accountDetail != null) {
                accountDto = new AccountDto();
                accountDto.setId(accountDetail.getId());
                accountDto.setAccountName(accountDetail.getAccountName());
                accountDto.setRealName(accountDetail.getRealName());
                accountDto.setAccountStatus(accountDetail.getAccountStatus());
                accountDto.setMerchantId(accountDetail.getMerchantId());
                accountDto.setStoreId(accountDetail.getStoreId());
                accountDto.setStaffId(accountDetail.getStaffId());
                if (accountDetail.getStoreId() != null && accountDetail.getStoreId() > 0) {
                    MtStore mtStore = storeService.queryStoreById(accountDetail.getStoreId());
                    if (mtStore != null) {
                        accountDto.setStoreName(mtStore.getName());
                    }
                }
                List<Long> roleIds = accountQueryService.getRoleIdsByAccountId(accountDetail.getId());
                result.put("roleIds", roleIds);
            }
        } else {
            result.put("roleIds", "");
        }

        result.put("account", accountDto);
        return getSuccessResult(result);
    }

    /**
     * 新增账户
     */
    @ApiOperation(value = "新增账户")
    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('system:account:add')")
    public ResponseObject doCreate(@RequestBody Map<String, Object> param) throws BusinessCheckException {
        AccountInfoDto account = TokenUtil.getAccountInfo();

        List<Integer> roleIds = (List) param.get("roleIds");
        String accountName = param.get("accountName").toString();
        String accountStatus = param.get("accountStatus").toString();
        String realName = param.get("realName").toString();
        String password = param.get("password").toString();
        String storeId = param.get("storeId") == null ? "0" : param.get("storeId").toString();
        String merchantId = param.get("merchantId") == null ? "0" : param.get("merchantId").toString();
        String staffId = param.get("staffId") == null ? "0" : param.get("staffId").toString();

        AccountInfoDto existAccount = accountQueryService.queryByName(accountName);
        if (existAccount != null) {
            return getFailureResult(201, "该用户名已存在");
        }

        List<TDuty> duties = new ArrayList<>();
        if (roleIds != null && roleIds.size() > 0) {
            Integer[] roles = roleIds.toArray(new Integer[roleIds.size()]);
            String[] ids = new String[roles.length];
            for (int i = 0; i < roles.length; i++) {
                ids[i] = roles[i].toString();
            }
            duties = tDutyService.findDatasByIds(ids);
            if (duties.size() < roleIds.size()) {
                return getFailureResult(201, "您分配的角色不存在");
            }
        }

        CreateAccountCommand command = new CreateAccountCommand();
        command.setAccountKey(CommonUtil.createAccountKey());
        command.setAccountName(accountName);
        command.setPassword(password);
        command.setRealName(realName);
        command.setStoreId(StringUtil.isNotEmpty(storeId) ? Integer.parseInt(storeId) : 0);
        command.setMerchantId(StringUtil.isNotEmpty(merchantId) ? Integer.parseInt(merchantId) : 0);
        command.setStaffId(StringUtil.isNotEmpty(staffId) ? Integer.parseInt(staffId) : 0);
        command.setDuties(duties);

        accountCommandService.createAccount(command);
        return getSuccessResult(true);
    }

    /**
     * 修改账户信息
     */
    @ApiOperation(value = "修改账户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('system:account:edit')")
    public ResponseObject update(@RequestBody Map<String, Object> param) throws BusinessCheckException {
        List<Integer> roleIds = (List) param.get("roleIds");
        String realName = param.get("realName").toString();
        String accountName = param.get("accountName").toString();
        String accountStatus = param.get("accountStatus").toString();
        String storeId = param.get("storeId") == null ? "" : param.get("storeId").toString();
        String staffId = param.get("staffId") == null ? "" : param.get("staffId").toString();
        String merchantId = param.get("merchantId") == null ? "" : param.get("merchantId").toString();
        Long id = Long.parseLong(param.get("id").toString());

        AccountInfoDto loginAccount = TokenUtil.getAccountInfo();
        AccountInfoDto targetAccount = accountQueryService.queryById(id.intValue());
        if (targetAccount == null) {
            return getFailureResult(201, "账户不存在");
        }
        if (loginAccount.getMerchantId() > 0 && !targetAccount.getMerchantId().equals(loginAccount.getMerchantId())) {
            return getFailureResult(1004);
        }

        AccountInfoDto existAccount = accountQueryService.queryByName(accountName);
        if (existAccount != null && existAccount.getId() != id.intValue()) {
            return getFailureResult(201, "该用户名已存在");
        }

        List<TDuty> duties = null;
        if (roleIds != null && roleIds.size() > 0) {
            Integer[] roles = roleIds.toArray(new Integer[roleIds.size()]);
            String[] ids = new String[roles.length];
            for (int i = 0; i < roles.length; i++) {
                ids[i] = roles[i].toString();
            }
            duties = tDutyService.findDatasByIds(ids);
            if (duties.size() < roleIds.size()) {
                return getFailureResult(201, "您分配的角色不存在");
            }
        }

        UpdateAccountCommand command = new UpdateAccountCommand();
        command.setAcctId(id.intValue());
        command.setRealName(realName);
        command.setAccountName(StringUtil.isNotEmpty(accountName) ? accountName : null);
        command.setStoreId(StringUtil.isNotEmpty(storeId) ? Integer.parseInt(storeId) : null);
        command.setStaffId(StringUtil.isNotEmpty(staffId) ? Integer.parseInt(staffId) : null);
        command.setMerchantId(StringUtil.isNotEmpty(merchantId) ? Integer.parseInt(merchantId) : null);
        command.setDuties(duties);

        accountCommandService.updateAccount(command);
        return getSuccessResult(true);
    }

    /**
     * 删除账户信息
     */
    @ApiOperation(value = "删除账户信息")
    @RequestMapping(value = "/delete/{userIds}", method = RequestMethod.GET)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('system:account:delete')")
    public ResponseObject deleteAccount(@PathVariable("userIds") String userIds) {
        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        String[] ids = userIds.split(",");
        for (String id : ids) {
            if (StringUtil.isNotEmpty(id)) {
                AccountInfoDto target = accountQueryService.queryById(Integer.parseInt(id));
                if (target == null) {
                    return getFailureResult(201, "账户不存在");
                }
                if (StringUtil.equals(accountInfo.getAccountName(), target.getAccountName())) {
                    return getFailureResult(201, "您不能删除自己");
                }
            }
        }
        for (String id : ids) {
            if (StringUtil.isNotEmpty(id)) {
                DeleteAccountCommand command = new DeleteAccountCommand();
                command.setAccountId(Long.parseLong(id));
                accountCommandService.deleteAccount(command);
            }
        }
        return getSuccessResult(true);
    }

    /**
     * 更新账户状态
     */
    @ApiOperation(value = "更新账户状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('system:account:edit')")
    public ResponseObject updateStatus(@RequestBody Map<String, Object> param) throws BusinessCheckException {
        Integer userId = param.get("userId") == null ? 0 : Integer.parseInt(param.get("userId").toString());
        Integer status = param.get("status") == null ? 0 : Integer.parseInt(param.get("status").toString());

        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        AccountInfoDto target = accountQueryService.queryById(userId);
        if (target == null || accountInfo == null) {
            return getFailureResult(201, "账户不存在");
        }

        accountCommandService.updateAccountStatus(userId, status);
        return getSuccessResult(true);
    }

    /**
     * 修改账户密码
     */
    @ApiOperation(value = "修改账户密码")
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @CrossOrigin
    @PreAuthorize("@pms.hasPermission('system:account:edit')")
    public ResponseObject resetPwd(@RequestBody Map<String, Object> param) {
        Integer userId = param.get("userId") == null ? 0 : Integer.parseInt(param.get("userId").toString());
        String password = param.get("password") == null ? "" : param.get("password").toString();

        AccountInfoDto accountInfo = TokenUtil.getAccountInfo();
        AccountInfoDto target = accountQueryService.queryById(userId);
        if (target == null) {
            return getFailureResult(201, "账户不存在");
        }
        if (accountInfo.getMerchantId() > 0 && !accountInfo.getMerchantId().equals(target.getMerchantId())) {
            return getFailureResult(1004);
        }

        accountCommandService.resetPassword(userId, password);
        return getSuccessResult(true);
    }
}
