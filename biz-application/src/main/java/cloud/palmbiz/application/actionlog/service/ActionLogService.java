package cloud.palmbiz.application.actionlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.TActionLog;

/**
 * 后台日志服务接口
 */
public interface ActionLogService extends IService<TActionLog> {

    /**
     * 保存日志
     *
     * @param actionLog
     * @return
     */
    void saveActionLog(TActionLog actionLog);

    /**
     * 获取分页查询数据
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<TActionLog> findLogsByPagination(PaginationRequest paginationRequest);
}
