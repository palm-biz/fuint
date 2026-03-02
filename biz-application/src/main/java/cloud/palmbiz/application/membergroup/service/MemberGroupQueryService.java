package cloud.palmbiz.application.membergroup.service;

import cloud.palmbiz.common.member.dto.UserGroupDto;
import cloud.palmbiz.common.service.MemberGroupService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtUserGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGroupQueryService {

    private final MemberGroupService memberGroupService;

    public PaginationResponse<UserGroupDto> queryMemberGroupListByPagination(PaginationRequest paginationRequest) {
        return memberGroupService.queryMemberGroupListByPagination(paginationRequest);
    }

    public MtUserGroup queryMemberGroupById(Integer id) {
        return memberGroupService.queryMemberGroupById(id);
    }
}
