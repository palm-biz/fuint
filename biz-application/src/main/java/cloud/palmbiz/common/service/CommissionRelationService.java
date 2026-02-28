package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.commission.dto.CommissionRelationDto;
import cloud.palmbiz.common.param.CommissionRelationPage;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtCommissionRelation;
import cloud.palmbiz.infrastructure.model.MtUser;

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
