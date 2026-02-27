package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.aftersale.dto.AftersaleDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtRefund;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.Map;

/**
 * 售后业务接口
 */
public interface RefundService extends IService<MtRefund> {

    /**
     * 分页查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<AftersaleDto> getRefundListByPagination(PaginationRequest paginationRequest);

    /**
     * 获取用户的售后订单
     * @param paramMap 查询参数
     * @return
     */
    ResponseObject getUserRefundList(Map<String, Object> paramMap);

    /**
     * 创建售后订单
     *
     * @param AftersaleDto
     * @return
     */
    MtRefund createRefund(AftersaleDto AftersaleDto);

    /**
     * 根据ID获取售后订单信息
     *
     * @param id ID
     * @return
     */
    AftersaleDto getRefundById(Integer id);

    /**
     * 根据订单ID获取售后订单信息
     *
     * @param  orderId
     * @return
     */
    MtRefund getRefundByOrderId(Integer orderId);

    /**
     * 更新售后订单
     * @param  reqDto
     * @throws BusinessCheckException
     */
    MtRefund updateRefund(AftersaleDto reqDto) throws BusinessCheckException;

    /**
     * 同意售后订单
     * @param  reqDto
     * @throws BusinessCheckException
     */
    MtRefund agreeRefund(AftersaleDto reqDto) throws BusinessCheckException;

    /**
     * 发起退款
     *
     * @param orderId 订单号
     * @param refundAmount 退款金额
     * @param remark 备注
     * @param accountInfo 操作人信息
     * throws BusinessCheckException;
     */
    Boolean doRefund(Integer orderId, String refundAmount, String remark, AccountInfoDto accountInfo) throws BusinessCheckException;

    /**
     * 获取售后订单总数
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    Long getRefundCount(Date beginTime, Date endTime);
}
