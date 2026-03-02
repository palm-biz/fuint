package cloud.palmbiz.application.account.service;

import cloud.palmbiz.application.account.dto.AccountDto;
import cloud.palmbiz.application.account.dto.AccountInfoDto;
import cloud.palmbiz.common.param.AccountPage;
import cloud.palmbiz.domain.account.model.Account;
import cloud.palmbiz.domain.account.model.AccountId;
import cloud.palmbiz.domain.account.repository.AccountRepository;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.mapper.MtMerchantMapper;
import cloud.palmbiz.infrastructure.mapper.MtStoreMapper;
import cloud.palmbiz.infrastructure.model.MtMerchant;
import cloud.palmbiz.infrastructure.model.MtStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号查询服务
 * 处理账号的查询操作
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class AccountQueryService {

    private final AccountRepository accountRepository;
    private final MtMerchantMapper mtMerchantMapper;
    private final MtStoreMapper mtStoreMapper;

    /**
     * 分页查询账号列表
     */
    public PaginationResponse<AccountDto> queryByPage(AccountPage accountPage) {
        // 查询账号列表
        List<Account> accounts = accountRepository.findByPage(accountPage);
        long total = accountRepository.getPageTotal();
        int pages = accountRepository.getPageCount();

        // 转换为DTO
        List<AccountDto> dataList = new ArrayList<>();
        for (Account account : accounts) {
            AccountDto accountDto = toAccountDto(account);
            dataList.add(accountDto);
        }

        // 构造分页响应
        PageRequest pageRequest = PageRequest.of(accountPage.getPage(), accountPage.getPageSize());
        PageImpl pageImpl = new PageImpl(dataList, pageRequest, total);
        PaginationResponse<AccountDto> paginationResponse = new PaginationResponse(pageImpl, AccountDto.class);
        paginationResponse.setTotalPages(pages);
        paginationResponse.setTotalElements(total);
        paginationResponse.setContent(dataList);

        return paginationResponse;
    }

    /**
     * 根据账号名查询账号信息
     */
    public AccountInfoDto queryByName(String accountName) {
        Account account = accountRepository.findByName(accountName);
        if (account == null || !account.isActive()) {
            return null;
        }
        return toAccountInfoDto(account);
    }

    /**
     * 根据ID查询账号信息
     */
    public AccountInfoDto queryById(Integer accountId) {
        Account account = accountRepository.findById(AccountId.of(accountId));
        if (account == null) {
            return null;
        }
        return toAccountInfoDto(account);
    }

    /**
     * 获取账号的角色ID列表
     */
    public List<Long> getRoleIdsByAccountId(Integer accountId) {
        return accountRepository.getRoleIdsByAccountId(accountId);
    }

    /**
     * 获取账号的职责ID列表
     */
    public List<Integer> getDutyIdsByAccountId(Integer accountId) {
        return accountRepository.getDutyIdsByAccountId(accountId);
    }

    // ==================== 转换方法 ====================

    /**
     * 领域对象转 AccountDto
     */
    private AccountDto toAccountDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getAccountIdValue());
        dto.setAccountKey(account.getAccountKey());
        dto.setAccountName(account.getAccountNameValue());
        dto.setAccountStatus(account.getStatusCode());
        dto.setRealName(account.getRealName());
        dto.setMerchantId(account.getMerchantId());
        dto.setStoreId(account.getStoreId());
        dto.setStaffId(account.getStaffId());
        dto.setCreateDate(account.getCreateDate());
        dto.setModifyDate(account.getModifyDate());
        dto.setLocked(account.getLocked() != null ? account.getLocked() : 0);
        dto.setIsActive(account.getIsActive() != null ? account.getIsActive() : 0);

        // 不返回敏感信息
        dto.setPassword(null);
        dto.setSalt(null);

        // 查询商户名称
        if (account.getMerchantId() != null && account.getMerchantId() > 0) {
            MtMerchant mtMerchant = mtMerchantMapper.selectById(account.getMerchantId());
            if (mtMerchant != null) {
                dto.setMerchantName(mtMerchant.getName());
            }
        }

        // 查询店铺名称
        if (account.getStoreId() != null && account.getStoreId() > 0) {
            MtStore mtStore = mtStoreMapper.selectById(account.getStoreId());
            if (mtStore != null) {
                dto.setStoreName(mtStore.getName());
            }
        }

        return dto;
    }

    /**
     * 领域对象转 AccountInfoDto
     */
    private AccountInfoDto toAccountInfoDto(Account account) {
        AccountInfoDto dto = new AccountInfoDto();
        dto.setId(account.getAccountIdValue());
        dto.setAccountKey(account.getAccountKey());
        dto.setAccountName(account.getAccountNameValue());
        dto.setAccountStatus(account.getStatusCode());
        dto.setRealName(account.getRealName());
        dto.setRoleIds(account.getRoleIds());
        dto.setMerchantId(account.getMerchantId() != null ? account.getMerchantId() : 0);
        dto.setStoreId(account.getStoreId());
        dto.setStaffId(account.getStaffId());
        dto.setCreateDate(account.getCreateDate());
        dto.setModifyDate(account.getModifyDate());
        dto.setLocked(account.getLocked() != null ? account.getLocked() : 0);
        dto.setOwnerId(account.getOwnerId() != null ? account.getOwnerId() : 0);
        dto.setIsActive(account.getIsActive() != null ? String.valueOf(account.getIsActive()) : "0");

        // 查询商户名称
        if (account.getMerchantId() != null && account.getMerchantId() > 0) {
            MtMerchant mtMerchant = mtMerchantMapper.selectById(account.getMerchantId());
            if (mtMerchant != null) {
                dto.setMerchantName(mtMerchant.getName());
            }
        }

        // 查询店铺名称
        if (account.getStoreId() != null && account.getStoreId() > 0) {
            MtStore mtStore = mtStoreMapper.selectById(account.getStoreId());
            if (mtStore != null) {
                dto.setStoreName(mtStore.getName());
            }
        }

        return dto;
    }
}
