package cloud.palmbiz.application.gencode.service;

import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.TGenCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenCodeCommandService {

    private final GenCodeService genCodeService;

    @Transactional(rollbackFor = Exception.class)
    public TGenCode addGenCode(TGenCode tGenCode) throws BusinessCheckException {
        return genCodeService.addGenCode(tGenCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public TGenCode updateGenCode(TGenCode tGenCode) {
        return genCodeService.updateGenCode(tGenCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public void generatorCode(String tableName) {
        genCodeService.generatorCode(tableName);
    }
}
