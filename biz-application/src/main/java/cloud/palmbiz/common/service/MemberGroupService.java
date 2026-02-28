package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.member.dto.MemberGroupDto;
import cloud.palmbiz.common.user.dto.UserGroupDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtUserGroup;

/**
 * 会员分组业务接口
 */
public interface MemberGroupService extends IService<MtUserGroup> {

    /**
     * 分页查询分组列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<UserGroupDto> queryMemberGroupListByPagination(PaginationRequest paginationRequest);

    /**
     * 新增会员分组
     *
     * @param  memberGroupDto
     * @return
     */
    MtUserGroup addMemberGroup(MemberGroupDto memberGroupDto);

    /**
     * 修改卡券分组
     *
     * @param  memberGroupDto
     * @throws BusinessCheckException
     */
    MtUserGroup updateMemberGroup(MemberGroupDto memberGroupDto) throws BusinessCheckException;

    /**
     * 根据组ID获取分组信息
     *
     * @param  id 分组ID
     * @return
     */
    MtUserGroup queryMemberGroupById(Integer id);

    /**
     * 根据分组ID删除分组信息
     *
     * @param  id 分组ID
     * @param  operator 操作人
     * @return
     */
    void deleteMemberGroup(Integer id, String operator);
}
