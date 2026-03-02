package cloud.palmbiz.application.sendlog.service;

import cloud.palmbiz.common.sendlog.dto.ReqSendLogDto;
import cloud.palmbiz.common.service.SendLogService;
import cloud.palmbiz.infrastructure.model.MtSendLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SendLogCommandService {

    private final SendLogService sendLogService;

    @Transactional(rollbackFor = Exception.class)
    public MtSendLog addSendLog(ReqSendLogDto reqSendLogDto) {
        return sendLogService.addSendLog(reqSendLogDto);
    }
}
