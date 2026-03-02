package cloud.palmbiz.application.identity.account.service;

import cloud.palmbiz.application.identity.account.command.CreateAccountCommand;
import cloud.palmbiz.application.identity.account.command.DeleteAccountCommand;
import cloud.palmbiz.application.identity.account.command.UpdateAccountCommand;
import cloud.palmbiz.application.staff.service.StaffService;
import cloud.palmbiz.application.store.service.StoreService;
import cloud.palmbiz.domain.account.model.Account;
import cloud.palmbiz.domain.account.model.AccountId;
import cloud.palmbiz.domain.account.repository.AccountRepository;
import cloud.palmbiz.framework.annoation.OperationServiceLog;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.exception.BusinessRuntimeException;
import cloud.palmbiz.infrastructure.mapper.TAccountMapper;
import cloud.palmbiz.infrastructure.model.MtStaff;
import cloud.palmbiz.infrastructure.model.MtStore;
import cloud.palmbiz.infrastructure.model.TAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号命令服务
 * 处理账号的创建、更新、删除等命令操作
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class AccountCommandService {

    private final AccountRepository accountRepository;
    private final StoreService storeService;
    private final StaffService staffService;
    private final TAccountMapper tAccountMapper;

    /**
     * 创建账号
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "新增后台账户")
    public TAccount createAccount(CreateAccountCommand command) throws BusinessCheckException {
        // 设置商户ID（如果未设置）
        Integer merchantId = command.getMerchantId();
        Integer storeId = command.getStoreId() == null ? 0 : command.getStoreId();
        if (merchantId == null || merchantId <= 0) {
            MtStore mtStore = storeService.queryStoreById(storeId);
            if (mtStore != null) {
                merchantId = mtStore.getMerchantId();
            }
        }

        // 创建领域对象
        Account account = Account.create(
                command.getAccountKey(),
                command.getAccountName(),
                command.getPassword(),
                command.getRealName(),
                merchantId,
                storeId,
                command.getStaffId()
        );

        // 设置角色
        account.updateBasicInfo(null, command.getRoleIds(), null, null, null);

        // 分配职责
        if (command.getDuties() != null && !command.getDuties().isEmpty()) {
            account.assignDuties(command.getDuties());
        }

        // 保存
        accountRepository.save(account);

        // 返回PO对象（保持向后兼容）
        return tAccountMapper.selectById(account.getAccountIdValue());
    }

    /**
     * 更新账号
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "修改后台账户")
    public void updateAccount(UpdateAccountCommand command) throws BusinessCheckException {
        // 加载聚合根
        Account account = accountRepository.findById(AccountId.of(command.getAcctId()));
        if (account == null) {
            throw new BusinessCheckException("账户不存在");
        }

        // 验证员工ID
        Integer staffId = command.getStaffId();
        if (staffId != null && staffId > 0) {
            MtStaff mtStaff = staffService.queryStaffById(staffId);
            if (mtStaff == null) {
                staffId = 0;
            }
        }

        // 更新基本信息
        account.updateBasicInfo(
                command.getRealName(),
                command.getRoleIds(),
                command.getMerchantId(),
                command.getStoreId(),
                staffId
        );

        // 分配职责
        if (command.getDuties() != null) {
            account.assignDuties(command.getDuties());
        }

        // 保存
        accountRepository.save(account);
    }

    /**
     * 删除账号（软删除）
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "删除后台账户")
    public void deleteAccount(DeleteAccountCommand command) {
        Account account = accountRepository.findById(AccountId.of(command.getAccountId().intValue()));
        if (account != null) {
            accountRepository.remove(account);
        }
    }

    /**
     * 更新账号状态
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "更新后台账户状态")
    public void updateAccountStatus(Integer accountId, Integer status) {
        Account account = accountRepository.findById(AccountId.of(accountId));
        if (account == null) {
            throw new BusinessRuntimeException("账户不存在");
        }

        if (status == 1) {
            account.enable();
        } else if (status == 0) {
            account.disable();
        } else if (status == -1) {
            account.delete();
        }

        accountRepository.save(account);
    }

    /**
     * 重置密码
     */
    @Transactional(rollbackFor = Exception.class)
    @OperationServiceLog(description = "重置后台账户密码")
    public void resetPassword(Integer accountId, String newPassword) {
        Account account = accountRepository.findById(AccountId.of(accountId));
        if (account == null) {
            throw new BusinessRuntimeException("账户不存在");
        }

        account.resetPassword(newPassword);
        accountRepository.save(account);
    }
}
