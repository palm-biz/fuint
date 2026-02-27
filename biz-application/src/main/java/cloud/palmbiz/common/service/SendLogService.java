package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.dto.ReqSendLogDto;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtSendLog;

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
