package cloud.palmbiz.domain.member.model;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 会员等级实体
 * 封装会员等级相关的业务规则
 *
 * @author DDD Refactoring
 */
@Getter
public class MemberGrade {

    /**
     * 等级ID
     */
    private Integer id;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 等级（数字）
     */
    private Integer grade;

    /**
     * 等级名称
     */
    private String name;

    /**
     * 升级条件描述
     */
    private String catchCondition;

    /**
     * 升级条件类型
     * init: 默认获取
     * pay: 付费升级
     * frequency: 消费次数
     * amount: 累积消费金额升级
     */
    private String catchType;

    /**
     * 达到升级条件的值
     */
    private BigDecimal catchValue;

    /**
     * 会员权益描述
     */
    private String userPrivilege;

    /**
     * 有效期（天数）
     */
    private Integer validDay;

    /**
     * 享受折扣
     */
    private Float discount;

    /**
     * 积分加速
     */
    private Float speedPoint;

    /**
     * 返利比例
     */
    private Float rebate;

    /**
     * 状态
     */
    private String status;

    // ==================== 构造方法 ====================

    /**
     * 从持久化数据重建
     */
    public static MemberGrade reconstitute(
            Integer id,
            Integer merchantId,
            Integer grade,
            String name,
            String catchCondition,
            String catchType,
            BigDecimal catchValue,
            String userPrivilege,
            Integer validDay,
            Float discount,
            Float speedPoint,
            Float rebate,
            String status) {
        MemberGrade memberGrade = new MemberGrade();
        memberGrade.id = id;
        memberGrade.merchantId = merchantId;
        memberGrade.grade = grade;
        memberGrade.name = name;
        memberGrade.catchCondition = catchCondition;
        memberGrade.catchType = catchType;
        memberGrade.catchValue = catchValue;
        memberGrade.userPrivilege = userPrivilege;
        memberGrade.validDay = validDay;
        memberGrade.discount = discount;
        memberGrade.speedPoint = speedPoint;
        memberGrade.rebate = rebate;
        memberGrade.status = status;
        return memberGrade;
    }

    // ==================== 业务方法 ====================

    /**
     * 是否为初始等级
     */
    public boolean isInitGrade() {
        return "init".equals(this.catchType);
    }

    /**
     * 是否为付费等级
     */
    public boolean isPaidGrade() {
        return "pay".equals(this.catchType);
    }

    /**
     * 检查是否满足升级条件
     *
     * @param value 当前值（消费次数或金额）
     * @return 是否满足
     */
    public boolean meetsUpgradeCondition(BigDecimal value) {
        if (this.catchValue == null) {
            return false;
        }
        return value.compareTo(this.catchValue) >= 0;
    }

    /**
     * 计算折扣后价格
     *
     * @param originalPrice 原价
     * @return 折扣后价格
     */
    public BigDecimal calculateDiscountPrice(BigDecimal originalPrice) {
        if (this.discount == null || this.discount >= 1.0f) {
            return originalPrice;
        }
        return originalPrice.multiply(new BigDecimal(this.discount.toString()));
    }

    /**
     * 计算积分加速
     *
     * @param basePoint 基础积分
     * @return 加速后积分
     */
    public Integer calculateSpeedPoint(Integer basePoint) {
        if (this.speedPoint == null || this.speedPoint <= 1.0f) {
            return basePoint;
        }
        return (int) (basePoint * this.speedPoint);
    }

    /**
     * 计算返利金额
     *
     * @param amount 消费金额
     * @return 返利金额
     */
    public BigDecimal calculateRebate(BigDecimal amount) {
        if (this.rebate == null || this.rebate <= 0) {
            return BigDecimal.ZERO;
        }
        return amount.multiply(new BigDecimal(this.rebate.toString()));
    }

    /**
     * 是否激活状态
     */
    public boolean isActive() {
        return "A".equals(this.status);
    }
}
