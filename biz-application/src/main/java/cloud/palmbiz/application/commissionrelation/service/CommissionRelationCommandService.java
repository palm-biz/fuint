package cloud.palmbiz.application.commissionrelation.service;

import cloud.palmbiz.common.service.CommissionRelationService;
import cloud.palmbiz.infrastructure.model.MtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommissionRelationCommandService {

    private final CommissionRelationService commissionRelationService;

    @Transactional(rollbackFor = Exception.class)
    public void setCommissionRelation(MtUser userInfo, String shareId) {
        commissionRelationService.setCommissionRelation(userInfo, shareId);
    }
}
