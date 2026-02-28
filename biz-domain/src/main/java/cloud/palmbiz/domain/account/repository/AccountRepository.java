package cloud.palmbiz.domain.account.repository;

import cloud.palmbiz.domain.account.model.Account;
import cloud.palmbiz.domain.account.model.AccountId;
import cloud.palmbiz.common.param.AccountPage;

import java.util.List;

/**
 * 账号仓储接口
 * 领域层定义，基础设施层实现
 *
 * @author DDD Refactoring
 */
public interface AccountRepository {

    /**
     * 根据ID查找账号
     *
     * @param id 账号ID
     * @return 账号聚合根，不存在返回null
     */
    Account findById(AccountId id);

    /**
     * 根据账号名查找账号
     *
     * @param accountName 账号名称
     * @return 账号聚合根，不存在返回null
     */
    Account findByName(String accountName);

    /**
     * 分页查询账号列表
     *
     * @param accountPage 查询条件
     * @return 账号列表
     */
    List<Account> findByPage(AccountPage accountPage);

    /**
     * 获取分页查询的总记录数
     *
     * @return 总记录数
     */
    long getPageTotal();

    /**
     * 获取分页查询的总页数
     *
     * @return 总页数
     */
    int getPageCount();

    /**
     * 保存账号（新建或更新）
     *
     * @param account 账号聚合根
     */
    void save(Account account);

    /**
     * 删除账号（软删除）
     *
     * @param account 账号聚合根
     */
    void remove(Account account);

    /**
     * 获取账号的角色ID列表
     *
     * @param accountId 账号ID
     * @return 角色ID列表
     */
    List<Long> getRoleIdsByAccountId(Integer accountId);

    /**
     * 获取账号的职责ID列表
     *
     * @param accountId 账号ID
     * @return 职责ID列表
     */
    List<Integer> getDutyIdsByAccountId(Integer accountId);
}
