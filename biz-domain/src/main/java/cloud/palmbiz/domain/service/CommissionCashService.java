package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.commission.dto.CommissionCashDto;
import cloud.palmbiz.common.param.CommissionCashPage;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtCommissionCash;
import cloud.palmbiz.module.backendApi.request.CommissionCashRequest;
import cloud.palmbiz.module.backendApi.request.CommissionSettleConfirmRequest;
import cloud.palmbiz.module.backendApi.request.CommissionSettleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 分销提成记录业务接口
 */
public interface CommissionCashService extends IService<MtCommissionCash> {

    /**
     * 分页查询列表
     *
     * @param commissionCashPage
     * @return
     */
    PaginationResponse<CommissionCashDto> queryCommissionCashByPagination(CommissionCashPage commissionCashPage);

    /**
     * 计算订单分销提成
     *
     * @param  commissionSettleRequest 结算参数
     * @throws BusinessCheckException
     * @return
     */
    String settleCommission(CommissionSettleRequest commissionSettleRequest) throws BusinessCheckException;

    /**
     * 根据ID获取记录信息
     *
     * @param  id 记录ID
     * @throws BusinessCheckException
     * @return
     */
    CommissionCashDto queryCommissionCashById(Integer id) throws BusinessCheckException;

    /**
     * 更新分销提成记录
     *
     * @param commissionCashRequest 请求参数
     * @throws BusinessCheckException
     * @return
     */
    void updateCommissionCash(CommissionCashRequest commissionCashRequest) throws BusinessCheckException;

    /**
     * 结算确认
     *
     * @param requestParam 确认参数
     * @throws BusinessCheckException
     * @return
     */
    void confirmCommissionCash(CommissionSettleConfirmRequest requestParam) throws BusinessCheckException;

    /**
     * 取消结算
     *
     * @param requestParam 取消参数
     * @throws BusinessCheckException
     * @return
     */
    void cancelCommissionCash(CommissionSettleConfirmRequest requestParam) throws BusinessCheckException;

    /**
     * 支付结算金额到用户余额
     *
     * @param commissionCashRequest 请求参数
     * @throws BusinessCheckException
     * @return
     */
    void payToBalance(CommissionCashRequest commissionCashRequest) throws BusinessCheckException;

}
