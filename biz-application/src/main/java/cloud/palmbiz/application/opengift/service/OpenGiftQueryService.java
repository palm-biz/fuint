package cloud.palmbiz.application.opengift.service;

import cloud.palmbiz.common.opengift.dto.OpenGiftDto;
import cloud.palmbiz.common.service.OpenGiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenGiftQueryService {

    private final OpenGiftService openGiftService;

    public Object getOpenGiftList(Map<String, Object> paramMap) {
        return openGiftService.getOpenGiftList(paramMap);
    }

    public OpenGiftDto getOpenGiftDetail(Integer id) throws cloud.palmbiz.framework.exception.BusinessCheckException {
        return openGiftService.getOpenGiftDetail(id);
    }
}
