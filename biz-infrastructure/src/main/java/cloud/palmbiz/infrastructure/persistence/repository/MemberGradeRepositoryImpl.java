package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.domain.member.model.MemberGrade;
import cloud.palmbiz.domain.member.repository.MemberGradeRepository;
import cloud.palmbiz.infrastructure.mapper.MtUserGradeMapper;
import cloud.palmbiz.infrastructure.model.MtUserGrade;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会员等级仓储实现
 *
 * @author DDD Refactoring
 */
@Repository
@AllArgsConstructor
public class MemberGradeRepositoryImpl implements MemberGradeRepository {

    private final MtUserGradeMapper mtUserGradeMapper;

    @Override
    public MemberGrade findById(Integer id) {
        MtUserGrade po = mtUserGradeMapper.selectById(id);
        if (po == null) {
            return null;
        }
        return toDomain(po);
    }

    @Override
    public List<MemberGrade> findByMerchantId(Integer merchantId) {
        LambdaQueryWrapper<MtUserGrade> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtUserGrade::getMerchantId, merchantId);
        wrapper.eq(MtUserGrade::getStatus, "A");
        wrapper.orderByAsc(MtUserGrade::getGrade);
        List<MtUserGrade> poList = mtUserGradeMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MemberGrade findInitGrade(Integer merchantId) {
        LambdaQueryWrapper<MtUserGrade> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtUserGrade::getMerchantId, merchantId);
        wrapper.eq(MtUserGrade::getCatchType, "init");
        wrapper.eq(MtUserGrade::getStatus, "A");
        wrapper.last("LIMIT 1");
        MtUserGrade po = mtUserGradeMapper.selectOne(wrapper);
        if (po == null) {
            return null;
        }
        return toDomain(po);
    }

    @Override
    public List<MemberGrade> findByParams(Map<String, Object> params) {
        List<MtUserGrade> poList = mtUserGradeMapper.selectByMap(params);
        return poList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(MemberGrade memberGrade) {
        MtUserGrade po = toPO(memberGrade);
        if (memberGrade.getId() == null) {
            mtUserGradeMapper.insert(po);
        } else {
            mtUserGradeMapper.updateById(po);
        }
    }

    // ==================== 转换方法 ====================

    private MemberGrade toDomain(MtUserGrade po) {
        return MemberGrade.reconstitute(
                po.getId(),
                po.getMerchantId(),
                po.getGrade(),
                po.getName(),
                po.getCatchCondition(),
                po.getCatchType(),
                po.getCatchValue(),
                po.getUserPrivilege(),
                po.getValidDay(),
                po.getDiscount(),
                po.getSpeedPoint(),
                po.getRebate(),
                po.getStatus()
        );
    }

    private MtUserGrade toPO(MemberGrade domain) {
        MtUserGrade po = new MtUserGrade();
        po.setId(domain.getId());
        po.setMerchantId(domain.getMerchantId());
        po.setGrade(domain.getGrade());
        po.setName(domain.getName());
        po.setCatchCondition(domain.getCatchCondition());
        po.setCatchType(domain.getCatchType());
        po.setCatchValue(domain.getCatchValue());
        po.setUserPrivilege(domain.getUserPrivilege());
        po.setValidDay(domain.getValidDay());
        po.setDiscount(domain.getDiscount());
        po.setSpeedPoint(domain.getSpeedPoint());
        po.setRebate(domain.getRebate());
        po.setStatus(domain.getStatus());
        return po;
    }
}
