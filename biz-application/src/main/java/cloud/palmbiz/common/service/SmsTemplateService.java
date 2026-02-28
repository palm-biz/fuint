package cloud.palmbiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.palmbiz.common.sms.dto.SmsTemplateDto;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtSmsTemplate;

import java.util.List;
import java.util.Map;

/**
 * 短信模板业务接口
 */
public interface SmsTemplateService extends IService<MtSmsTemplate> {

    /**
     * 分页查询模板列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<MtSmsTemplate> querySmsTemplateListByPagination(PaginationRequest paginationRequest);

    /**
     * 添加模板
     *
     * @param reqSmsTemplateDto
     * @throws BusinessCheckException
     * @return
     */
    MtSmsTemplate saveSmsTemplate(SmsTemplateDto reqSmsTemplateDto) throws BusinessCheckException;

    /**
     * 删除短信模板
     * @param id
     * @param operator
     * @return
     */
    void deleteTemplate(Integer id, String operator);

    /**
     * 根据模板ID获取模板信息
     *
     * @param id ID
     * @return
     */
    MtSmsTemplate querySmsTemplateById(Integer id);

    /**
     * 根据条件搜索模板
     *
     * @param params 搜索条件
     * @return
     */
    List<MtSmsTemplate> querySmsTemplateByParams(Map<String, Object> params);

}
