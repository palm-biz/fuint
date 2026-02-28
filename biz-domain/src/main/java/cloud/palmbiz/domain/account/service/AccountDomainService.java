package cloud.palmbiz.domain.account.service;

import cloud.palmbiz.domain.account.model.Account;
import cloud.palmbiz.domain.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 账号领域服务
 * 处理跨聚合的业务逻辑
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class AccountDomainService {

    private final AccountRepository accountRepository;

    /**
     * 检查账号名是否已存在
     *
     * @param accountName 账号名
     * @return 是否已存在
     */
    public boolean isAccountNameExists(String accountName) {
        Account account = accountRepository.findByName(accountName);
        return account != null;
    }

    /**
     * 检查账号名是否可用（排除自身）
     *
     * @param accountName 账号名
     * @param excludeAccountId 排除的账号ID
     * @return 是否可用
     */
    public boolean isAccountNameAvailable(String accountName, Integer excludeAccountId) {
        Account account = accountRepository.findByName(accountName);
        if (account == null) {
            return true;
        }
        return account.getAccountIdValue().equals(excludeAccountId);
    }
}
