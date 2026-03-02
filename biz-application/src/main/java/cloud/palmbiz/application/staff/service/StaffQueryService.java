package cloud.palmbiz.application.staff.service;

import cloud.palmbiz.common.staff.dto.StaffDto;
import cloud.palmbiz.common.service.StaffService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtStaff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StaffQueryService {

    private final StaffService staffService;

    public PaginationResponse<StaffDto> queryStaffListByPagination(PaginationRequest paginationRequest) {
        return staffService.queryStaffListByPagination(paginationRequest);
    }

    public MtStaff queryStaffById(Integer id) {
        return staffService.queryStaffById(id);
    }

    public List<MtStaff> queryStaffByParams(Map<String, Object> params) {
        return staffService.queryStaffByParams(params);
    }

    public MtStaff queryStaffByMobile(String mobile) {
        return staffService.queryStaffByMobile(mobile);
    }

    public MtStaff queryStaffByUserId(Integer userId) {
        return staffService.queryStaffByUserId(userId);
    }

    public StaffDto getStaffInfoByMobile(String mobile) {
        return staffService.getStaffInfoByMobile(mobile);
    }
}
