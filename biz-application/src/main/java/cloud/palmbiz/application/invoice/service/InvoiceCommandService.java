package cloud.palmbiz.application.invoice.service;

import cloud.palmbiz.common.invoice.param.InvoiceParam;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtInvoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvoiceCommandService {

    private final InvoiceService invoiceService;

    @Transactional(rollbackFor = Exception.class)
    public MtInvoice addInvoice(InvoiceParam invoice) throws BusinessCheckException {
        return invoiceService.addInvoice(invoice);
    }

    @Transactional(rollbackFor = Exception.class)
    public MtInvoice updateInvoice(InvoiceParam invoice) throws BusinessCheckException {
        return invoiceService.updateInvoice(invoice);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteInvoice(Integer id, String operator) {
        invoiceService.deleteInvoice(id, operator);
    }
}
