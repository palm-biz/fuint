package cloud.palmbiz.application.cate.service;

import cloud.palmbiz.common.service.CateService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtGoodsCate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CateCommandService {

    private final CateService cateService;

    @Transactional(rollbackFor = Exception.class)
    public MtGoodsCate addCate(MtGoodsCate reqDto) throws BusinessCheckException {
        return cateService.addCate(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtGoodsCate updateCate(MtGoodsCate reqDto) throws BusinessCheckException {
        return cateService.updateCate(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCate(Integer id, String operator) throws BusinessCheckException {
        cateService.deleteCate(id, operator);
    }
}
