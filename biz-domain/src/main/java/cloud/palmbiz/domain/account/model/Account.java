package cloud.palmbiz.domain.account.model;

import cloud.palmbiz.infrastructure.model.TDuty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账号聚合根
 * 封装账号相关的业务规则和行为
 *
 * @author DDD Refactoring
 */
@Getter
public class Account {

    /**
     * 账号ID
     */
    private AccountId accountId;

    /**
     * 账号编码
     */
    private String accountKey;

    /**
     * 账号名称
     */
    private AccountName accountName;

    /**
     * 密码
     */
    private Password password;

    /**
     * 账号状态
     */
    private AccountStatus status;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 角色ID列表（逗号分隔的字符串）
     */
    private String roleIds;

    /**
     * 所属商户ID
     */
    private Integer merchantId;

    /**
     * 所属店铺ID
     */
    private Integer storeId;

    /**
     * 关联员工ID
     */
    private Integer staffId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 是否锁定
     */
    private Integer locked;

    /**
     * 所属平台
     */
    private Integer ownerId;

    /**
     * 是否激活
     */
    private Integer isActive;

    /**
     * 分配的职责列表
     */
    private List<Integer> dutyIds;

    // ==================== 构造函数 ====================

    /**
     * 创建新账号（用于新建）
     */
    public static Account create(
            String accountKey,
            String accountName,
            String plainPassword,
            String realName,
            Integer merchantId,
            Integer storeId,
            Integer staffId) {
        Account account = new Account();
        account.accountKey = accountKey;
        account.accountName = AccountName.of(accountName);
        account.password = Password.fromPlainText(plainPassword);
        account.status = AccountStatus.ENABLED;
        account.realName = realName;
        account.merchantId = merchantId;
        account.storeId = storeId;
        account.staffId = staffId;
        account.createDate = new Date();
        account.modifyDate = new Date();
        account.locked = 0;
        account.isActive = 1;
        account.dutyIds = new ArrayList<>();
        return account;
    }

    /**
     * 从持久化数据重建（用于从数据库加载）
     */
    public static Account reconstitute(
            Integer acctId,
            String accountKey,
            String accountName,
            String encryptedPassword,
            String salt,
            Integer accountStatus,
            String realName,
            String roleIds,
            Integer merchantId,
            Integer storeId,
            Integer staffId,
            Date createDate,
            Date modifyDate,
            Integer locked,
            Integer ownerId,
            Integer isActive,
            List<Integer> dutyIds) {
        Account account = new Account();
        account.accountId = AccountId.of(acctId);
        account.accountKey = accountKey;
        account.accountName = AccountName.of(accountName);
        account.password = Password.fromEncrypted(encryptedPassword, salt);
        account.status = AccountStatus.fromCode(accountStatus);
        account.realName = realName;
        account.roleIds = roleIds;
        account.merchantId = merchantId;
        account.storeId = storeId;
        account.staffId = staffId;
        account.createDate = createDate;
        account.modifyDate = modifyDate;
        account.locked = locked;
        account.ownerId = ownerId;
        account.isActive = isActive;
        account.dutyIds = dutyIds != null ? new ArrayList<>(dutyIds) : new ArrayList<>();
        return account;
    }

    // ==================== 业务方法 ====================

    /**
     * 启用账号
     */
    public void enable() {
        if (this.status == AccountStatus.DELETED) {
            throw new IllegalStateException("已删除的账号不能被启用");
        }
        this.status = AccountStatus.ENABLED;
        this.modifyDate = new Date();
    }

    /**
     * 禁用账号
     */
    public void disable() {
        if (this.status == AccountStatus.DELETED) {
            throw new IllegalStateException("已删除的账号不能被禁用");
        }
        this.status = AccountStatus.DISABLED;
        this.modifyDate = new Date();
    }

    /**
     * 删除账号（软删除）
     */
    public void delete() {
        this.status = AccountStatus.DELETED;
        this.modifyDate = new Date();
    }

    /**
     * 分配角色
     */
    public void assignDuties(List<TDuty> duties) {
        if (duties == null || duties.isEmpty()) {
            this.dutyIds = new ArrayList<>();
            return;
        }
        this.dutyIds = new ArrayList<>();
        for (TDuty duty : duties) {
            this.dutyIds.add(duty.getDutyId());
        }
        this.modifyDate = new Date();
    }

    /**
     * 验证密码
     */
    public boolean verifyPassword(String plainPassword) {
        return this.password.matches(plainPassword);
    }

    /**
     * 修改密码
     */
    public void changePassword(String newPlainPassword) {
        this.password = this.password.change(newPlainPassword);
        this.modifyDate = new Date();
    }

    /**
     * 重置密码
     */
    public void resetPassword(String newPlainPassword) {
        changePassword(newPlainPassword);
    }

    /**
     * 是否为激活状态
     */
    public boolean isActive() {
        return this.status.isActive();
    }

    /**
     * 更新基本信息
     */
    public void updateBasicInfo(String realName, String roleIds, Integer merchantId, Integer storeId, Integer staffId) {
        if (realName != null) {
            this.realName = realName;
        }
        if (roleIds != null) {
            this.roleIds = roleIds;
        }
        if (merchantId != null) {
            this.merchantId = merchantId;
        }
        if (storeId != null) {
            this.storeId = storeId;
        }
        if (staffId != null) {
            this.staffId = staffId;
        }
        this.modifyDate = new Date();
    }

    /**
     * 设置账号ID（在持久化后设置）
     */
    public void setAccountId(Integer acctId) {
        if (this.accountId == null) {
            this.accountId = AccountId.of(acctId);
        }
    }

    /**
     * 获取账号ID的Integer值
     */
    public Integer getAccountIdValue() {
        return accountId != null ? accountId.getValue() : null;
    }

    /**
     * 获取账号名称字符串
     */
    public String getAccountNameValue() {
        return accountName.getValue();
    }

    /**
     * 获取加密密码
     */
    public String getEncryptedPassword() {
        return password.getEncryptedValue();
    }

    /**
     * 获取盐值
     */
    public String getSalt() {
        return password.getSalt();
    }

    /**
     * 获取状态码
     */
    public Integer getStatusCode() {
        return status.getCode();
    }
}
