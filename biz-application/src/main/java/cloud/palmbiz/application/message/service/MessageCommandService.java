package cloud.palmbiz.application.message.service;

import cloud.palmbiz.common.service.MessageService;
import cloud.palmbiz.infrastructure.model.MtMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageCommandService {

    private final MessageService messageService;

    @Transactional(rollbackFor = Exception.class)
    public void addMessage(MtMessage reqMsgDto) {
        messageService.addMessage(reqMsgDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void readMessage(Integer msgId) {
        messageService.readMessage(msgId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(Integer msgId, boolean isRead) {
        messageService.sendMessage(msgId, isRead);
    }
}
