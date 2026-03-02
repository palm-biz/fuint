package cloud.palmbiz.application.opengift.service;

import cloud.palmbiz.common.service.OpenGiftService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtOpenGift;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OpenGiftCommandService {

    private final OpenGiftService openGiftService;

    @Transactional(rollbackFor = Exception.class)
    public MtOpenGift addOpenGift(MtOpenGift reqDto) throws BusinessCheckException {
        return openGiftService.addOpenGift(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtOpenGift updateOpenGift(MtOpenGift reqDto) throws BusinessCheckException {
        return openGiftService.updateOpenGift(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOpenGift(Integer id, String operator) throws BusinessCheckException {
        openGiftService.deleteOpenGift(id, operator);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean openGift(Integer userId, Integer gradeId, boolean isNewMember) throws BusinessCheckException {
        return openGiftService.openGift(userId, gradeId, isNewMember);
    }
}
