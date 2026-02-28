package cloud.palmbiz.application.member.command;

import lombok.Data;

/**
 * 调整积分命令
 *
 * @author DDD Refactoring
 */
@Data
public class AdjustPointCommand {

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 调整积分（正数为增加，负数为扣减）
     */
    private Integer points;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * 操作人
     */
    private String operator;
}
