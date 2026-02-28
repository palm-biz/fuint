package cloud.palmbiz.application.account.query;

import lombok.Data;

/**
 * 账号查询条件
 *
 * @author DDD Refactoring
 */
@Data
public class AccountQuery {

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 账户状态
     */
    private String accountStatus;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 员工ID
     */
    private Integer staffId;
}
