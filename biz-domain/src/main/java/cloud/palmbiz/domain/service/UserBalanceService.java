package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.param.UserBalancePage;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtUserBalance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员余额业务接口
 */
public interface UserBalanceService extends IService<MtUserBalance> {

    /**
     * 分页查询列表
     *
     * @param userBalancePage
     * @return
     */
    PaginationResponse<MtUserBalance> queryUserBalanceListByPagination(UserBalancePage userBalancePage);

    /**
     * 添加会员余额
     *
     * @param  mtUserBalance
     * @throws BusinessCheckException
     * @return
     */
    MtUserBalance addUserBalance(MtUserBalance mtUserBalance) throws BusinessCheckException;

    /**
     * 根据ID获取会员余额信息
     *
     * @param id ID
     * @throws BusinessCheckException
     * @return
     */
    MtUserBalance queryUserBalanceById(Integer id);

    /**
     * 更新会员余额
     * @param  mtUserBalance
     * @throws BusinessCheckException
     * @return
     */
    MtUserBalance updateUserBalance(MtUserBalance mtUserBalance) throws BusinessCheckException;

}
