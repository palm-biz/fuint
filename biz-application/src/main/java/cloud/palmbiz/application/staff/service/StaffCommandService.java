package cloud.palmbiz.application.staff.service;

import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtStaff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffCommandService {

    private final StaffService staffService;

    @Transactional(rollbackFor = Exception.class)
    public MtStaff saveStaff(MtStaff reqStaff, String operator) throws BusinessCheckException {
        return staffService.saveStaff(reqStaff, operator);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateAuditedStatus(Integer staffId, String status, String operator) {
        return staffService.updateAuditedStatus(staffId, status, operator);
    }
}
