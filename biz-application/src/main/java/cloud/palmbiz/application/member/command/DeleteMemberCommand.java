package cloud.palmbiz.application.member.command;

import lombok.Data;

/**
 * 删除会员命令
 *
 * @author DDD Refactoring
 */
@Data
public class DeleteMemberCommand {

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 操作人
     */
    private String operator;
}
