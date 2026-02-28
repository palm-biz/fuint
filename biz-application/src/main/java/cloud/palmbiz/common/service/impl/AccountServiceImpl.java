package cloud.palmbiz.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cloud.palmbiz.application.account.command.CreateAccountCommand;
import cloud.palmbiz.application.account.command.DeleteAccountCommand;
import cloud.palmbiz.application.account.command.LoginCommand;
import cloud.palmbiz.application.account.command.UpdateAccountCommand;
import cloud.palmbiz.application.account.service.AccountAuthService;
import cloud.palmbiz.application.account.service.AccountCommandService;
import cloud.palmbiz.application.account.service.AccountQueryService;
import cloud.palmbiz.common.account.dto.AccountDto;
import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.param.AccountPage;
import cloud.palmbiz.common.service.AccountService;
import cloud.palmbiz.framework.annoation.OperationServiceLog;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.module.backendApi.request.LoginRequest;
import cloud.palmbiz.module.backendApi.response.LoginResponse;
import cloud.palmbiz.infrastructure.mapper.TAccountMapper;
import cloud.palmbiz.infrastructure.model.TAccount;
import cloud.palmbiz.infrastructure.model.TDuty;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 后台账号接口（Facade模式）
 * 保持向后兼容，内部委托给新的应用服务
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor(onConstructor_= {@Lazy})
public class AccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount> implements AccountService {

    private final TAccountMapper tAccountMapper;
    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;
    private final AccountAuthService accountAuthService;

    /**
     * 分页查询账号列表
     * 委托给 AccountQueryService
     *
     * @param accountPage 分页参数
     * @return 分页结果
     */
    @Override
    public PaginationResponse<AccountDto> getAccountListByPagination(AccountPage accountPage) {
        return accountQueryService.queryByPage(accountPage);
    }

    /**
     * 根据账号名称获取账号信息
     * 委托给 AccountQueryService
     *
     * @param userName 账号名称
     * @return 账号信息
     */
    @Override
    public AccountInfoDto getAccountByName(String userName) {
        return accountQueryService.queryByName(userName);
    }

    /**
     * 根据ID获取账号信息
     * 委托给 AccountQueryService
     *
     * @param userId 账号ID
     * @return 账号实体
     */
    @Override
    public TAccount getAccountInfoById(Integer userId) {
        return tAccountMapper.selectById(userId);
    }

    /**
     * 新增后台账户
     * 委托给 AccountCommandService
     *
     * @param tAccount 账户实体
     * @param duties 职责列表
     * @return 创建的账户
     * @throws BusinessCheckException 业务异常
     */
    @Override
    @OperationServiceLog(description = "新增后台账户")
    public TAccount createAccountInfo(TAccount tAccount, List<TDuty> duties) throws BusinessCheckException {
        CreateAccountCommand command = new CreateAccountCommand();
        command.setAccountKey(tAccount.getAccountKey());
        command.setAccountName(tAccount.getAccountName());
        command.setPassword(tAccount.getPassword());
        command.setRealName(tAccount.getRealName());
        command.setRoleIds(tAccount.getRoleIds());
        command.setMerchantId(tAccount.getMerchantId());
        command.setStoreId(tAccount.getStoreId());
        command.setStaffId(tAccount.getStaffId());
        command.setDuties(duties);

        return accountCommandService.createAccount(command);
    }

    /**
     * 获取账号角色ID
     * 委托给 AccountQueryService
     *
     * @param accountId 账号ID
     * @return 角色ID列表
     */
    @Override
    public List<Long> getRoleIdsByAccountId(Integer accountId) {
        return accountQueryService.getRoleIdsByAccountId(accountId);
    }

    /**
     * 修改账户
     * 委托给 AccountCommandService
     *
     * @param  tAccount 账户实体
     * @param duties 职责列表
     * @throws BusinessCheckException 业务异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "修改后台账户")
    public void editAccount(TAccount tAccount, List<TDuty> duties) throws BusinessCheckException {
        UpdateAccountCommand command = new UpdateAccountCommand();
        command.setAcctId(tAccount.getAcctId());
        command.setAccountKey(tAccount.getAccountKey());
        command.setAccountName(tAccount.getAccountName());
        command.setRealName(tAccount.getRealName());
        command.setRoleIds(tAccount.getRoleIds());
        command.setMerchantId(tAccount.getMerchantId());
        command.setStoreId(tAccount.getStoreId());
        command.setStaffId(tAccount.getStaffId());
        command.setDuties(duties);

        accountCommandService.updateAccount(command);
    }

    /**
     * 根据账户名称获取账户所分配的角色ID集合
     * 委托给 AccountQueryService
     *
     * @param  accountId 账户ID
     * @return 角色ID集合
     */
    @Override
    public List<Integer> getDutyIdsByAccountId(Integer accountId) {
        return accountQueryService.getDutyIdsByAccountId(accountId);
    }

    /**
     * 更新账户
     * 委托给底层 Mapper
     *
     * @param tAccount 账户实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "修改后台账户")
    public void updateAccount(TAccount tAccount) {
        tAccount.setModifyDate(new Date());
        tAccountMapper.updateById(tAccount);
    }

    /**
     * 删除账号
     * 委托给 AccountCommandService
     *
     * @param accountId 账号ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "删除后台账户")
    public void deleteAccount(Long accountId) {
        DeleteAccountCommand command = new DeleteAccountCommand();
        command.setAccountId(accountId);
        accountCommandService.deleteAccount(command);
    }

    /**
     * 设定安全的密码（保留用于向后兼容）
     * 使用领域模型的密码加密逻辑
     *
     * @param tAccount 账号信息
     */
    @Override
    public void entryptPassword(TAccount tAccount) {
        cloud.palmbiz.domain.account.model.Password password =
            cloud.palmbiz.domain.account.model.Password.fromPlainText(tAccount.getPassword());
        tAccount.setSalt(password.getSalt());
        tAccount.setPassword(password.getEncryptedValue());
    }

    /**
     * 获取加密密码（保留用于向后兼容）
     * 使用领域模型的密码加密逻辑
     *
     * @param password 明文密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    @Override
    public String getEntryptPassword(String password, String salt) {
        byte[] saltBytes = cloud.palmbiz.common.utils.Encodes.decodeHex(salt);
        byte[] hashPassword = cloud.palmbiz.common.utils.Digests.sha1(password.getBytes(), saltBytes, 1024);
        return cloud.palmbiz.common.utils.Encodes.encodeHex(hashPassword);
    }

    /**
     * 登录后台系统
     * 委托给 AccountAuthService
     *
     * @param loginRequest 登录参数
     * @param userAgent 登录浏览器
     * @return 登录响应
     * @throws BusinessCheckException 业务异常
     */
    @Override
    @OperationServiceLog(description = "登录后台系统")
    public LoginResponse doLogin(LoginRequest loginRequest, String userAgent) throws BusinessCheckException {
        LoginCommand command = new LoginCommand();
        command.setUsername(loginRequest.getUsername());
        command.setPassword(loginRequest.getPassword());
        command.setCaptchaCode(loginRequest.getCaptchaCode());
        command.setUuid(loginRequest.getUuid());
        command.setUserAgent(userAgent);

        return accountAuthService.login(command);
    }
}
