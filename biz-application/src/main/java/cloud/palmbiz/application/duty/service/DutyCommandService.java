package cloud.palmbiz.application.duty.service;

import cloud.palmbiz.common.service.DutyService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.interfaces.backendApi.request.DutyStatusRequest;
import cloud.palmbiz.infrastructure.model.TDuty;
import cloud.palmbiz.infrastructure.model.TSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DutyCommandService {

    private final DutyService dutyService;

    @Transactional(rollbackFor = Exception.class)
    public void saveDuty(TDuty duty, List<TSource> sources) throws BusinessCheckException {
        dutyService.saveDuty(duty, sources);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDuty(Integer merchantId, long dutyId) throws BusinessCheckException {
        dutyService.deleteDuty(merchantId, dutyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer merchantId, DutyStatusRequest dutyStatusRequest) throws BusinessCheckException {
        dutyService.updateStatus(merchantId, dutyStatusRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDuty(TDuty tduty, List<TSource> sources) throws BusinessCheckException {
        dutyService.updateDuty(tduty, sources);
    }
}
