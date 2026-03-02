package cloud.palmbiz.application.message.service;

import cloud.palmbiz.common.service.MessageService;
import cloud.palmbiz.infrastructure.model.MtMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageQueryService {

    private final MessageService messageService;

    public MtMessage getOne(Integer userId) {
        return messageService.getOne(userId);
    }

    public List<MtMessage> getNeedSendList() {
        return messageService.getNeedSendList();
    }
}
