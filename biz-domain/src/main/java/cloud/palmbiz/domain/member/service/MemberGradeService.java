package cloud.palmbiz.domain.member.service;

import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.model.MemberGrade;
import cloud.palmbiz.domain.member.repository.MemberGradeRepository;
import cloud.palmbiz.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 会员等级领域服务
 * 处理会员等级相关的业务逻辑
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class MemberGradeService {

    private final MemberGradeRepository memberGradeRepository;
    private final MemberRepository memberRepository;

    /**
     * 获取初始会员等级
     *
     * @param merchantId 商户ID
     * @return 初始等级
     */
    public MemberGrade getInitGrade(Integer merchantId) {
        return memberGradeRepository.findInitGrade(merchantId);
    }

    /**
     * 检查会员是否需要升级
     *
     * @param member 会员
     * @param consumeAmount 累计消费金额
     * @param consumeFrequency 消费次数
     * @return 是否需要升级
     */
    public boolean needsUpgrade(Member member, BigDecimal consumeAmount, Integer consumeFrequency) {
        List<MemberGrade> grades = memberGradeRepository.findByMerchantId(member.getMerchantId());
        MemberGrade currentGrade = memberGradeRepository.findById(member.getGradeId());

        if (currentGrade == null || grades.isEmpty()) {
            return false;
        }

        // 查找是否有更高等级满足升级条件
        for (MemberGrade grade : grades) {
            if (grade.getGrade() <= currentGrade.getGrade()) {
                continue; // 跳过当前或更低等级
            }

            boolean meets = false;
            if ("amount".equals(grade.getCatchType())) {
                meets = grade.meetsUpgradeCondition(consumeAmount);
            } else if ("frequency".equals(grade.getCatchType())) {
                meets = grade.meetsUpgradeCondition(new BigDecimal(consumeFrequency));
            }

            if (meets) {
                return true;
            }
        }

        return false;
    }

    /**
     * 自动升级会员等级
     *
     * @param member 会员
     * @param consumeAmount 累计消费金额
     * @param consumeFrequency 消费次数
     */
    public void autoUpgradeGrade(Member member, BigDecimal consumeAmount, Integer consumeFrequency) {
        List<MemberGrade> grades = memberGradeRepository.findByMerchantId(member.getMerchantId());
        MemberGrade currentGrade = memberGradeRepository.findById(member.getGradeId());

        if (currentGrade == null || grades.isEmpty()) {
            return;
        }

        // 找到最高可升级的等级
        MemberGrade targetGrade = null;
        for (MemberGrade grade : grades) {
            if (grade.getGrade() <= currentGrade.getGrade()) {
                continue;
            }

            boolean meets = false;
            if ("amount".equals(grade.getCatchType())) {
                meets = grade.meetsUpgradeCondition(consumeAmount);
            } else if ("frequency".equals(grade.getCatchType())) {
                meets = grade.meetsUpgradeCondition(new BigDecimal(consumeFrequency));
            }

            if (meets) {
                if (targetGrade == null || grade.getGrade() > targetGrade.getGrade()) {
                    targetGrade = grade;
                }
            }
        }

        // 执行升级
        if (targetGrade != null) {
            Date endTime = calculateEndTime(targetGrade.getValidDay());
            member.upgradeGrade(targetGrade.getId(), endTime);
            memberRepository.save(member);
        }
    }

    /**
     * 检查并处理会员过期
     *
     * @param member 会员
     */
    public void checkAndHandleExpiration(Member member) {
        if (member.isExpired()) {
            MemberGrade initGrade = getInitGrade(member.getMerchantId());
            if (initGrade != null && !member.getGradeId().equals(initGrade.getId())) {
                member.resetToInitGrade(initGrade.getId());
                memberRepository.save(member);
            }
        }
    }

    /**
     * 计算会员到期时间
     *
     * @param validDay 有效天数
     * @return 到期时间
     */
    private Date calculateEndTime(Integer validDay) {
        if (validDay == null || validDay <= 0) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, validDay);
        return calendar.getTime();
    }
}
