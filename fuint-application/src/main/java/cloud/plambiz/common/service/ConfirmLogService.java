package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.ConfirmLogDto;
import cloud.plambiz.common.param.ConfirmLogPage;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtConfirmLog;

import java.util.Date;
import java.util.List;

/**
 * 核销记录业务接口
 */
public interface ConfirmLogService extends IService<MtConfirmLog> {

    /**
     * 分页查询会员卡券核销列表
     *
     * @param confirmLogPage
     * @return
     */
    PaginationResponse<ConfirmLogDto> queryConfirmLogListByPagination(ConfirmLogPage confirmLogPage);

    /**
     * 获取卡券核销次数
     * @param userCouponId
     * @return
     * */
    Long getConfirmNum(Integer userCouponId);

    /**
     * 获取卡券核销列表
     * @param userCouponId
     * @return
     * */
    List<MtConfirmLog> getConfirmList(Integer userCouponId);

    /**
     * 获取核销总数
     * */
    Long getConfirmCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime);
}
