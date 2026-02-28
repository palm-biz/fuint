package cloud.palmbiz.infrastructure.repository;

import cloud.palmbiz.domain.account.model.Account;
import cloud.palmbiz.domain.account.model.AccountId;
import cloud.palmbiz.domain.account.repository.AccountRepository;
import cloud.palmbiz.common.param.AccountPage;
import cloud.palmbiz.infrastructure.mapper.TAccountDutyMapper;
import cloud.palmbiz.infrastructure.mapper.TAccountMapper;
import cloud.palmbiz.infrastructure.mapper.TDutyMapper;
import cloud.palmbiz.infrastructure.model.TAccount;
import cloud.palmbiz.infrastructure.model.TAccountDuty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号仓储实现
 * 实现领域层定义的 AccountRepository 接口
 *
 * @author DDD Refactoring
 */
@Repository
@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final TAccountMapper tAccountMapper;
    private final TDutyMapper tDutyMapper;
    private final TAccountDutyMapper tAccountDutyMapper;

    // 用于存储分页信息
    private ThreadLocal<Page<?>> pageThreadLocal = new ThreadLocal<>();

    @Override
    public Account findById(AccountId id) {
        TAccount tAccount = tAccountMapper.selectById(id.getValue());
        if (tAccount == null) {
            return null;
        }
        return toDomain(tAccount);
    }

    @Override
    public Account findByName(String accountName) {
        Map<String, Object> param = new HashMap<>();
        param.put("account_name", accountName.toLowerCase());
        param.put("account_status", 1);
        List<TAccount> accountList = tAccountMapper.selectByMap(param);
        if (accountList == null || accountList.isEmpty()) {
            return null;
        }
        return toDomain(accountList.get(0));
    }

    @Override
    public List<Account> findByPage(AccountPage accountPage) {
        Page<?> pageHelper = PageHelper.startPage(accountPage.getPage(), accountPage.getPageSize());
        pageThreadLocal.set(pageHelper);

        LambdaQueryWrapper<TAccount> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.ne(TAccount::getAccountStatus, -1); // 1:启用；0:禁用；-1:删除

        String name = accountPage.getAccountName();
        if (StringUtils.isNotEmpty(name)) {
            lambdaQueryWrapper.like(TAccount::getAccountName, name);
        }
        String realName = accountPage.getRealName();
        if (StringUtils.isNotEmpty(realName)) {
            lambdaQueryWrapper.like(TAccount::getRealName, realName);
        }
        String status = accountPage.getAccountStatus();
        if (StringUtils.isNotEmpty(status)) {
            lambdaQueryWrapper.eq(TAccount::getAccountStatus, status);
        }
        Integer merchantId = accountPage.getMerchantId();
        if (merchantId != null && merchantId > 0) {
            lambdaQueryWrapper.eq(TAccount::getMerchantId, merchantId);
        }
        Integer storeId = accountPage.getStoreId();
        if (storeId != null && storeId > 0) {
            lambdaQueryWrapper.eq(TAccount::getStoreId, storeId);
        }
        Integer staffId = accountPage.getStaffId();
        if (staffId != null && staffId > 0) {
            lambdaQueryWrapper.eq(TAccount::getStaffId, staffId);
        }

        lambdaQueryWrapper.orderByDesc(TAccount::getAcctId);
        List<TAccount> accountList = tAccountMapper.selectList(lambdaQueryWrapper);

        return accountList.stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public long getPageTotal() {
        Page<?> page = pageThreadLocal.get();
        if (page != null) {
            long total = page.getTotal();
            pageThreadLocal.remove();
            return total;
        }
        return 0;
    }

    @Override
    public int getPageCount() {
        Page<?> page = pageThreadLocal.get();
        if (page != null) {
            int pages = page.getPages();
            pageThreadLocal.remove();
            return pages;
        }
        return 0;
    }

    @Override
    public void save(Account account) {
        if (account.getAccountId() == null) {
            // 新建
            TAccount tAccount = toPO(account);
            tAccountMapper.insert(tAccount);
            // 设置生成的ID
            account.setAccountId(tAccount.getAcctId());

            // 保存职责关联
            saveDuties(account);
        } else {
            // 更新
            TAccount tAccount = toPO(account);
            tAccountMapper.updateById(tAccount);

            // 更新职责关联
            saveDuties(account);
        }
    }

    @Override
    public void remove(Account account) {
        account.delete();
        save(account);
    }

    @Override
    public List<Long> getRoleIdsByAccountId(Integer accountId) {
        return tDutyMapper.getRoleIdsByAccountId(accountId);
    }

    @Override
    public List<Integer> getDutyIdsByAccountId(Integer accountId) {
        return tAccountDutyMapper.getDutyIdsByAccountId(accountId);
    }

    // ==================== 转换方法 ====================

    /**
     * PO 转领域对象
     */
    private Account toDomain(TAccount po) {
        List<Integer> dutyIds = tAccountDutyMapper.getDutyIdsByAccountId(po.getAcctId());

        return Account.reconstitute(
                po.getAcctId(),
                po.getAccountKey(),
                po.getAccountName(),
                po.getPassword(),
                po.getSalt(),
                po.getAccountStatus(),
                po.getRealName(),
                po.getRoleIds(),
                po.getMerchantId(),
                po.getStoreId(),
                po.getStaffId(),
                po.getCreateDate(),
                po.getModifyDate(),
                po.getLocked(),
                po.getOwnerId(),
                po.getIsActive(),
                dutyIds
        );
    }

    /**
     * 领域对象转 PO
     */
    private TAccount toPO(Account domain) {
        TAccount po = new TAccount();
        if (domain.getAccountId() != null) {
            po.setAcctId(domain.getAccountIdValue());
        }
        po.setAccountKey(domain.getAccountKey());
        po.setAccountName(domain.getAccountNameValue());
        po.setPassword(domain.getEncryptedPassword());
        po.setSalt(domain.getSalt());
        po.setAccountStatus(domain.getStatusCode());
        po.setRealName(domain.getRealName());
        po.setRoleIds(domain.getRoleIds());
        po.setMerchantId(domain.getMerchantId());
        po.setStoreId(domain.getStoreId());
        po.setStaffId(domain.getStaffId());
        po.setCreateDate(domain.getCreateDate());
        po.setModifyDate(domain.getModifyDate());
        po.setLocked(domain.getLocked());
        po.setOwnerId(domain.getOwnerId());
        po.setIsActive(domain.getIsActive());
        return po;
    }

    /**
     * 保存职责关联
     */
    private void saveDuties(Account account) {
        if (account.getAccountId() == null) {
            return;
        }

        Integer accountId = account.getAccountIdValue();

        // 删除旧的关联
        tAccountDutyMapper.deleteDutiesByAccountId(accountId);

        // 插入新的关联
        List<Integer> dutyIds = account.getDutyIds();
        if (dutyIds != null && !dutyIds.isEmpty()) {
            for (Integer dutyId : dutyIds) {
                TAccountDuty tAccountDuty = new TAccountDuty();
                tAccountDuty.setAcctId(accountId);
                tAccountDuty.setDutyId(dutyId);
                tAccountDutyMapper.insert(tAccountDuty);
            }
        }
    }
}
