package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.param.UserBalancePage;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtUserBalance;

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
     * */
    MtUserBalance updateUserBalance(MtUserBalance mtUserBalance) throws BusinessCheckException;

}
