package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.CommissionRelationDto;
import cloud.plambiz.common.param.CommissionRelationPage;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtCommissionRelation;
import cloud.plambiz.repository.model.MtUser;

/**
 * 分销提成关系业务接口
 */
public interface CommissionRelationService extends IService<MtCommissionRelation> {

    /**
     * 分页查询分佣关系列表
     *
     * @param commissionRelationPage
     * @return
     */
    PaginationResponse<CommissionRelationDto> queryRelationByPagination(CommissionRelationPage commissionRelationPage);

    /**
     * 设置分销提成关系
     *
     * @param  userInfo 会员信息
     * @param  shareId 分享者ID
     * @retrurn
     */
    void setCommissionRelation(MtUser userInfo, String shareId);
}
