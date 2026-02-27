package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.module.backendApi.request.DutyStatusRequest;
import cloud.palmbiz.infrastructure.model.TDuty;
import cloud.palmbiz.infrastructure.model.TSource;
import cloud.palmbiz.common.domain.TreeNode;
import java.util.List;

/**
 * 角色服务接口
 */
public interface DutyService extends IService<TDuty> {
    
    /**
     * 角色保存方法
     *
     * @param duty 角色信息
     * @return
     */
    void saveDuty(TDuty duty, List<TSource> sources) throws BusinessCheckException;

    /**
     * 获取有效的角色集合
     *
     * @param merchantId 商户ID
     * @param accountId  账号ID
     * @return
     */
    List<TDuty> getAvailableRoles(Integer merchantId, Integer accountId);

    /**
     * 根据ID获取角色实体
     *
     * @param roleId 角色ID
     * @return
     */
    TDuty getRoleById(Long roleId);

    /**
     * 角色信息分页查询
     *
     * @param paginationRequest 分页查询请求对象
     * @return 分页查询结果对象
     */
    PaginationResponse<TDuty> findDutiesByPagination(PaginationRequest paginationRequest);

    /**
     * 根据ID数组获取角色集合
     *
     * @param ids 角色ID
     * @return
     */
    List<TDuty> findDatasByIds(String[] ids);

    /**
     * 删除方法
     *
     * @param merchantId 商户ID
     * @param dutyId 角色ID
     * @return
     */
    void deleteDuty(Integer merchantId, long dutyId) throws BusinessCheckException;

    /**
     * 更新状态
     *
     * @param merchantId
     * @param dutyStatusRequest
     * @return
     */
    void updateStatus(Integer merchantId, DutyStatusRequest dutyStatusRequest) throws BusinessCheckException;

    /**
     * 修改角色
     *
     * @param tduty 角色信息
     * @param sources 菜单列表
     * @return
     */
    void updateDuty(TDuty tduty, List<TSource> sources) throws BusinessCheckException;

    /**
     * 根据角色名称合状态查询角色
     *
     * @param merchantId 商户ID
     * @param name 角色名称
     * @return
     */
    TDuty findByName(Integer merchantId, String name);

    /**
     * 根据角色名称获取已经分配的菜单ID集合
     *
     * @param dutyId 角色ID
     * @return
     */
    List<Long> getSourceIdsByDutyId(Integer dutyId);

    /**
     * 获取角色的树形结构
     *
     * @param merchantId 商户ID
     * @return
     */
    List<TreeNode> getDutyTree(Integer merchantId);

    /**
     * 根据账户获取角色
     *
     * @param accountId 账号ID
     * @return
     */
    List<Long> findDutiesByAccountId(Integer accountId);
}
