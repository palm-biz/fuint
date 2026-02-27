package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.dto.CommissionRuleDto;
import cloud.palmbiz.common.param.CommissionRulePage;
import cloud.palmbiz.common.param.CommissionRuleParam;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtCommissionRule;

/**
 * 分销提成规则业务接口
 */
public interface CommissionRuleService extends IService<MtCommissionRule> {

    /**
     * 分页查询列表
     *
     * @param commissionRulePage
     * @return
     */
    PaginationResponse<MtCommissionRule> queryDataByPagination(CommissionRulePage commissionRulePage);

    /**
     * 添加分佣提成规则
     *
     * @param  commissionRule
     * @throws BusinessCheckException
     */
    MtCommissionRule addCommissionRule(CommissionRuleParam commissionRule) throws BusinessCheckException;

    /**
     * 根据ID获取规则信息
     *
     * @param  id
     * @return
     */
    CommissionRuleDto queryCommissionRuleById(Integer id);

    /**
     * 更新分佣提成规则
     *
     * @param  commissionRule
     * @return
     */
    MtCommissionRule updateCommissionRule(CommissionRuleParam commissionRule) throws BusinessCheckException;

}
