package cloud.palmbiz.application.identity.user.usergrade.service;

import cloud.palmbiz.application.identity.user.service.UserGradeService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtUserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGradeCommandService {

    private final UserGradeService userGradeService;

    @Transactional(rollbackFor = Exception.class)
    public MtUserGrade addUserGrade(MtUserGrade reqDto) throws BusinessCheckException {
        return userGradeService.addUserGrade(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtUserGrade updateUserGrade(MtUserGrade reqDto) throws BusinessCheckException {
        return userGradeService.updateUserGrade(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteUserGrade(Integer id, String operator) {
        return userGradeService.deleteUserGrade(id, operator);
    }
}
