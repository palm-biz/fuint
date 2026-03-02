package cloud.palmbiz.application.marketing.commission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.commission.dto.CommissionLogDto;
import cloud.palmbiz.common.param.CommissionLogPage;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.module.backendApi.request.CommissionLogRequest;
import cloud.palmbiz.infrastructure.model.MtCommissionLog;

/**
 * 分销提成记录业务接口
 */
public interface CommissionLogService extends IService<MtCommissionLog> {

    /**
     * 分页查询列表
     *
     * @param commissionLogPage
     * @return
     */
    PaginationResponse<CommissionLogDto> queryCommissionLogByPagination(CommissionLogPage commissionLogPage);

    /**
     * 计算订单分销提成
     *
     * @param  orderId 订单ID
     * @return
     */
    void calculateCommission(Integer orderId);

    /**
     * 根据ID获取记录信息
     *
     * @param  id 记录ID
     * @return
     */
    CommissionLogDto queryCommissionLogById(Integer id);

    /**
     * 更新分销提成记录
     *
     * @param requestParam 请求参数
     * @throws BusinessCheckException
     * @return
     */
    void updateCommissionLog(CommissionLogRequest requestParam) throws BusinessCheckException;
}
