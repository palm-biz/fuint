package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.GiveDto;
import cloud.plambiz.common.param.GiveParam;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.framework.web.ResponseObject;
import cloud.plambiz.repository.model.MtGive;
import cloud.plambiz.repository.model.MtGiveItem;

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
     * */
    List<MtGiveItem> queryItemByParams(Map<String, Object> params);
}
