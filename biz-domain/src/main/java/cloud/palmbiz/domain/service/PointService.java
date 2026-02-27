package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.dto.PointDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtPoint;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 积分业务接口
 */
public interface PointService extends IService<MtPoint> {

    /**
     * 分页查询积分列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<PointDto> queryPointListByPagination(PaginationRequest paginationRequest);

    /**
     * 添加积分
     *
     * @param  reqPointDto
     * @throws BusinessCheckException
     * @return
     */
    void addPoint(MtPoint reqPointDto) throws BusinessCheckException;

    /**
     * 转赠积分
     *
     * @param userId
     * @param mobile
     * @param amount
     * @param remark
     * @throws BusinessCheckException
     * @return
     */
    boolean doGift(Integer userId, String mobile, Integer amount, String remark) throws BusinessCheckException;
}
