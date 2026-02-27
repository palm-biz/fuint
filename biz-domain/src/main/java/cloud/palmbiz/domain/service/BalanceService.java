package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.balance.dto.BalanceDto;
import cloud.palmbiz.common.param.BalancePage;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtBalance;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 余额业务接口
 */
public interface BalanceService extends IService<MtBalance> {

    /**
     * 分页查询余额列表
     *
     * @param balancePage
     * @return
     */
    PaginationResponse<BalanceDto> queryBalanceListByPagination(BalancePage balancePage);

    /**
     * 添加余额记录
     *
     * @param reqDto
     * @param updateBalance
     * @throws BusinessCheckException
     */
    Boolean addBalance(MtBalance reqDto, Boolean updateBalance) throws BusinessCheckException;

    /**
     * 发放余额
     *
     * @param accountInfo 账号信息
     * @param object 发放对象，all全部
     * @param userIds 会员ID
     * @param amount 发放金额
     * @param remark 备注
     * @return
     */
    void distribute(AccountInfoDto accountInfo, String object, String userIds, String amount, String remark) throws BusinessCheckException;

    /**
     * 获取订单余额记录
     *
     * @param orderSn
     * @return
     */
    List<MtBalance> getBalanceListByOrderSn(String orderSn);
}
