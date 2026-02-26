package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.CommissionRuleDto;
import cloud.plambiz.common.param.CommissionRulePage;
import cloud.plambiz.common.param.CommissionRuleParam;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtCommissionRule;

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
     * */
    MtCommissionRule updateCommissionRule(CommissionRuleParam commissionRule) throws BusinessCheckException;

}
