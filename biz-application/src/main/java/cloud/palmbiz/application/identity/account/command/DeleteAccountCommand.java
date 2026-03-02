package cloud.palmbiz.application.identity.account.command;

import lombok.Data;

/**
 * 删除账号命令
 *
 * @author DDD Refactoring
 */
@Data
public class DeleteAccountCommand {

    /**
     * 账号ID
     */
    private Long accountId;
}
