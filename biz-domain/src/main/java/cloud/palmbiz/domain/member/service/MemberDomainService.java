package cloud.palmbiz.domain.member.service;

import cloud.palmbiz.domain.member.model.Member;
import cloud.palmbiz.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会员领域服务
 * 处理跨聚合的业务逻辑
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberRepository;

    /**
     * 检查手机号是否已存在
     *
     * @param merchantId 商户ID
     * @param mobile 手机号
     * @return 是否已存在
     */
    public boolean isMobileExists(Integer merchantId, String mobile) {
        Member member = memberRepository.findByMobile(merchantId, mobile);
        return member != null;
    }

    /**
     * 检查会员号是否已存在
     *
     * @param merchantId 商户ID
     * @param memberNo 会员号
     * @return 是否已存在
     */
    public boolean isMemberNoExists(Integer merchantId, String memberNo) {
        Member member = memberRepository.findByMemberNo(merchantId, memberNo);
        return member != null;
    }

    /**
     * 检查会员号是否可用（排除自身）
     *
     * @param merchantId 商户ID
     * @param memberNo 会员号
     * @param excludeMemberId 排除的会员ID
     * @return 是否可用
     */
    public boolean isMemberNoAvailable(Integer merchantId, String memberNo, Integer excludeMemberId) {
        Member member = memberRepository.findByMemberNo(merchantId, memberNo);
        if (member == null) {
            return true;
        }
        return member.getMemberIdValue().equals(excludeMemberId);
    }

    /**
     * 检查会员名称是否已存在
     *
     * @param merchantId 商户ID
     * @param name 会员名称
     * @return 是否已存在
     */
    public boolean isMemberNameExists(Integer merchantId, String name) {
        Member member = memberRepository.findByName(merchantId, name);
        return member != null;
    }
}
