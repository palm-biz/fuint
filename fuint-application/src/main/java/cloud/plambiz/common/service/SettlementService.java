package cloud.plambiz.common.service;

import cloud.plambiz.common.dto.SettlementDto;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.module.backendApi.request.SettlementRequest;
import cloud.plambiz.repository.model.MtSettlement;

/**
 * 订单结算相关业务接口
 */
public interface SettlementService {

    /**
     * 分页查询结算列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<MtSettlement> querySettlementListByPagination(PaginationRequest paginationRequest);

    /**
     * 提交结算
     *
     * @param  requestParam
     * @throws BusinessCheckException
     * @return
     */
    Boolean submitSettlement(SettlementRequest requestParam) throws BusinessCheckException;

    /**
     * 结算确认
     *
     * @param  settlementId
     * @param  operator
     * @throws BusinessCheckException
     * @return
     */
    Boolean doConfirm(Integer settlementId, String operator) throws BusinessCheckException;

    /**
     * 获取结算详情
     *
     * @param settlementId
     * @param page
     * @param pageSize
     * @return
     * */
    SettlementDto getSettlementInfo(Integer settlementId, Integer page, Integer pageSize) throws BusinessCheckException;
}
