package cloud.palmbiz.application.payment.refund.service;

import cloud.palmbiz.common.refund.dto.AftersaleDto;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtRefund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefundQueryService {

    private final RefundService refundService;

    public PaginationResponse<AftersaleDto> getRefundListByPagination(PaginationRequest paginationRequest) {
        return refundService.getRefundListByPagination(paginationRequest);
    }

    public AftersaleDto getRefundById(Integer id) {
        return refundService.getRefundById(id);
    }

    public MtRefund getRefundByOrderId(Integer orderId) {
        return refundService.getRefundByOrderId(orderId);
    }

    public Long getRefundCount(Date beginTime, Date endTime) {
        return refundService.getRefundCount(beginTime, endTime);
    }
}
