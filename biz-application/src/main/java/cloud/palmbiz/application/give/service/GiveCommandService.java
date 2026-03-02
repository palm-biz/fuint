package cloud.palmbiz.application.give.service;

import cloud.palmbiz.common.give.param.GiveParam;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.web.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GiveCommandService {

    private final GiveService giveService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseObject addGive(GiveParam giveParam) throws BusinessCheckException {
        return giveService.addGive(giveParam);
    }
}
