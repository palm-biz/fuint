package cloud.palmbiz.application.refund.service;

import cloud.palmbiz.application.account.dto.AccountInfoDto;
import cloud.palmbiz.common.refund.dto.AftersaleDto;
import cloud.palmbiz.common.service.RefundService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtRefund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundCommandService {

    private final RefundService refundService;

    @Transactional(rollbackFor = Exception.class)
    public MtRefund createRefund(AftersaleDto aftersaleDto) {
        return refundService.createRefund(aftersaleDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtRefund updateRefund(AftersaleDto reqDto) throws BusinessCheckException {
        return refundService.updateRefund(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtRefund agreeRefund(AftersaleDto reqDto) throws BusinessCheckException {
        return refundService.agreeRefund(reqDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean doRefund(Integer orderId, String refundAmount, String remark, AccountInfoDto accountInfo) throws BusinessCheckException {
        return refundService.doRefund(orderId, refundAmount, remark, accountInfo);
    }
}
