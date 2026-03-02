package cloud.palmbiz.application.smstemplate.service;

import cloud.palmbiz.common.service.SmsTemplateService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtSmsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmsTemplateQueryService {

    private final SmsTemplateService smsTemplateService;

    public PaginationResponse<MtSmsTemplate> querySmsTemplateListByPagination(PaginationRequest paginationRequest) {
        return smsTemplateService.querySmsTemplateListByPagination(paginationRequest);
    }

    public MtSmsTemplate querySmsTemplateById(Integer id) {
        return smsTemplateService.querySmsTemplateById(id);
    }

    public List<MtSmsTemplate> querySmsTemplateByParams(Map<String, Object> params) {
        return smsTemplateService.querySmsTemplateByParams(params);
    }
}
