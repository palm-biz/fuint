package cloud.palmbiz.application.staff.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.staff.dto.StaffDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtStaff;
import java.util.List;
import java.util.Map;

/**
 * 店铺员工业务接口
 */
public interface StaffService extends IService<MtStaff> {

    /**
     * 员工查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<StaffDto> queryStaffListByPagination(PaginationRequest paginationRequest);

    /**
     * 保存员工信息
     *
     * @param reqStaff 员工信息
     * @param operator 操作人
     * @throws BusinessCheckException
     * @return
     */
    MtStaff saveStaff(MtStaff reqStaff, String operator) throws BusinessCheckException;

    /**
     * 根据ID获取店铺信息
     *
     * @param  id 员工id
     */
    MtStaff queryStaffById(Integer id);

    /**
     * 审核更改状态(禁用，审核通过)
     *
     * @param staffId 员工ID
     * @param status 状态
     * @param operator 操作人
     * @return
     */
    Integer updateAuditedStatus(Integer staffId, String status, String operator);

    /**
     * 根据条件搜索员工
     *
     * @param params 请求参数
     * @return
     */
    List<MtStaff> queryStaffByParams(Map<String, Object> params);

    /**
     * 根据手机号获取员工信息
     *
     * @param  mobile 手机
     * @return
     */
    MtStaff queryStaffByMobile(String mobile);

    /**
     * 根据会员ID获取员工信息
     *
     * @param userId 会员ID
     * @return
     */
    MtStaff queryStaffByUserId(Integer userId);

    /**
     * 根据手机号获取员工信息
     *
     * @param  mobile 手机
     * @return
     */
    StaffDto getStaffInfoByMobile(String mobile);
}
