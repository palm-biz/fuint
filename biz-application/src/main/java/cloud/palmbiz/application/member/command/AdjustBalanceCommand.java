package cloud.palmbiz.application.member.command;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 调整余额命令
 *
 * @author DDD Refactoring
 */
@Data
public class AdjustBalanceCommand {

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 调整金额（正数为增加，负数为扣减）
     */
    private BigDecimal amount;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * 操作人
     */
    private String operator;
}
