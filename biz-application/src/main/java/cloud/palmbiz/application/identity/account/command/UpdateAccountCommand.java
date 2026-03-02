package cloud.palmbiz.application.identity.account.command;

import cloud.palmbiz.infrastructure.model.TDuty;
import lombok.Data;

import java.util.List;

/**
 * 更新账号命令
 *
 * @author DDD Refactoring
 */
@Data
public class UpdateAccountCommand {

    /**
     * 账号ID
     */
    private Integer acctId;

    /**
     * 账户编码
     */
    private String accountKey;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 角色ID列表
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
     * 员工ID
     */
    private Integer staffId;

    /**
     * 职责列表
     */
    private List<TDuty> duties;
}
