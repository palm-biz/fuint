package cloud.palmbiz.application.member.membergroup.service;

import cloud.palmbiz.common.member.dto.MemberGroupDto;
import cloud.palmbiz.application.member.service.MemberGroupService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtUserGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberGroupCommandService {

    private final MemberGroupService memberGroupService;

    @Transactional(rollbackFor = Exception.class)
    public MtUserGroup addMemberGroup(MemberGroupDto memberGroupDto) {
        return memberGroupService.addMemberGroup(memberGroupDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtUserGroup updateMemberGroup(MemberGroupDto memberGroupDto) throws BusinessCheckException {
        return memberGroupService.updateMemberGroup(memberGroupDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberGroup(Integer id, String operator) {
        memberGroupService.deleteMemberGroup(id, operator);
    }
}
