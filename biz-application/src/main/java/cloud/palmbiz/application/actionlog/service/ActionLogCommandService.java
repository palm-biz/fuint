package cloud.palmbiz.application.actionlog.service;

import cloud.palmbiz.common.service.ActionLogService;
import cloud.palmbiz.infrastructure.model.TActionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActionLogCommandService {

    private final ActionLogService actionLogService;

    @Transactional(rollbackFor = Exception.class)
    public void saveActionLog(TActionLog actionLog) {
        actionLogService.saveActionLog(actionLog);
    }
}
