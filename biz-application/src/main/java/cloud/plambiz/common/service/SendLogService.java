package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.ReqSendLogDto;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtSendLog;

/**
 * 发券记录业务接口
 */
public interface SendLogService extends IService<MtSendLog> {

    /**
     * 分页查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<MtSendLog> querySendLogListByPagination(PaginationRequest paginationRequest);

    /**
     * 添加记录
     *
     * @param  reqSendLogDto
     * @return
     */
    MtSendLog addSendLog(ReqSendLogDto reqSendLogDto);

    /**
     * 根据组ID获取发券记录
     *
     * @param  id ID
     * @return
     */
    MtSendLog querySendLogById(Long id);

}
