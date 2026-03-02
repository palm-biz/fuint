package cloud.palmbiz.application.invoice.service;

import cloud.palmbiz.common.service.InvoiceService;
import cloud.palmbiz.framework.pagination.PaginationRequest;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtInvoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceQueryService {

    private final InvoiceService invoiceService;

    public PaginationResponse<MtInvoice> queryInvoiceListByPagination(PaginationRequest paginationRequest) {
        return invoiceService.queryInvoiceListByPagination(paginationRequest);
    }

    public MtInvoice queryInvoiceById(Integer id) {
        return invoiceService.queryInvoiceById(id);
    }

    public List<MtInvoice> queryInvoiceListByParams(Map<String, Object> params) {
        return invoiceService.queryInvoiceListByParams(params);
    }

    public BigDecimal getInvoiceTotalAmount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        return invoiceService.getInvoiceTotalAmount(merchantId, storeId, beginTime, endTime);
    }
}
