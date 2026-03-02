package cloud.palmbiz.application.give.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.give.dto.GiveDto;
import cloud.palmbiz.common.param.GiveParam;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.infrastructure.model.MtGive;
import cloud.palmbiz.infrastructure.model.MtGiveItem;

import java.util.List;
import java.util.Map;

/**
 * 转赠业务接口
 */
public interface GiveService extends IService<MtGive> {

    /**
     * 分页查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<GiveDto> queryGiveListByPagination(PaginationRequest paginationRequest);

    /**
     * 转赠卡券
     *
     * @param giveParam
     * @throws BusinessCheckException
     * @return
     */
    ResponseObject addGive(GiveParam giveParam) throws BusinessCheckException;

    /**
     * 根据ID获取信息
     *
     * @param id ID
     * @return
     */
    MtGive queryGiveById(Long id);

    /**
     * 根据条件搜索转赠详情
     *
     * @param params
     * @return
     */
    List<MtGiveItem> queryItemByParams(Map<String, Object> params);
}
