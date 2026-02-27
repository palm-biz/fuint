package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.dto.ConfirmLogDto;
import cloud.palmbiz.common.param.ConfirmLogPage;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.WriteOffRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * 核销记录业务接口
 */
public interface ConfirmLogService extends IService<WriteOffRecord> {

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
     */
    Long getConfirmNum(Integer userCouponId);

    /**
     * 获取卡券核销列表
     * @param userCouponId
     * @return
     */
    List<WriteOffRecord> getConfirmList(Integer userCouponId);

    /**
     * 获取核销总数
     */
    Long getConfirmCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime);
}
