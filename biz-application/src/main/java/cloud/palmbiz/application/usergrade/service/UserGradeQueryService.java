package cloud.palmbiz.application.usergrade.service;

import cloud.palmbiz.common.service.UserGradeService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtUser;
import cloud.palmbiz.infrastructure.model.MtUserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGradeQueryService {

    private final UserGradeService userGradeService;

    public PaginationResponse<MtUserGrade> queryUserGradeListByPagination(PaginationRequest paginationRequest) {
        return userGradeService.queryUserGradeListByPagination(paginationRequest);
    }

    public MtUserGrade queryUserGradeById(Integer merchantId, Integer gradeId, Integer userId) {
        return userGradeService.queryUserGradeById(merchantId, gradeId, userId);
    }

    public MtUserGrade getInitUserGrade(Integer merchantId) {
        return userGradeService.getInitUserGrade(merchantId);
    }

    public List<MtUserGrade> getPayUserGradeList(Integer merchantId, MtUser userInfo) {
        return userGradeService.getPayUserGradeList(merchantId, userInfo);
    }

    public List<MtUserGrade> getMerchantGradeList(Integer merchantId, String status) {
        return userGradeService.getMerchantGradeList(merchantId, status);
    }
}
