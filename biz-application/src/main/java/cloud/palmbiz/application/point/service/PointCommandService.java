package cloud.palmbiz.application.point.service;

import cloud.palmbiz.common.service.PointService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointCommandService {

    private final PointService pointService;

    @Transactional(rollbackFor = Exception.class)
    public void addPoint(MtPoint reqPointDto) throws BusinessCheckException {
        pointService.addPoint(reqPointDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean doGift(Integer userId, String mobile, Integer amount, String remark) throws BusinessCheckException {
        return pointService.doGift(userId, mobile, amount, remark);
    }
}
