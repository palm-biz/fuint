package cloud.palmbiz.application.smstemplate.service;

import cloud.palmbiz.common.service.SmsTemplateService;
import cloud.palmbiz.common.smstemplate.dto.SmsTemplateDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtSmsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SmsTemplateCommandService {

    private final SmsTemplateService smsTemplateService;

    @Transactional(rollbackFor = Exception.class)
    public MtSmsTemplate saveSmsTemplate(SmsTemplateDto reqSmsTemplateDto) throws BusinessCheckException {
        return smsTemplateService.saveSmsTemplate(reqSmsTemplateDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(Integer id, String operator) {
        smsTemplateService.deleteTemplate(id, operator);
    }
}
