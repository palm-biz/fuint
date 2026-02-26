package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.param.InvoiceParam;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtInvoice;
import cloud.plambiz.framework.exception.BusinessCheckException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 发票业务接口
 */
public interface InvoiceService extends IService<MtInvoice> {

    /**
     * 分页查询列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<MtInvoice> queryInvoiceListByPagination(PaginationRequest paginationRequest);

    /**
     * 添加发票
     *
     * @param  invoice
     * @throws BusinessCheckException
     * @return
     */
    MtInvoice addInvoice(InvoiceParam invoice) throws BusinessCheckException;

    /**
     * 根据ID获取发票信息
     *
     * @param id ID
     * @return
     */
    MtInvoice queryInvoiceById(Integer id);

    /**
     * 根据ID删除发票
     *
     * @param id ID
     * @param operator 操作人
     * @return
     */
    void deleteInvoice(Integer id, String operator);

    /**
     * 更新发票信息
     *
     * @param  invoice
     * @throws BusinessCheckException
     * @return
     * */
    MtInvoice updateInvoice(InvoiceParam invoice) throws BusinessCheckException;

    /**
     * 根据条件搜索发票
     *
     * @param  params 查询参数
     * @return
     * */
    List<MtInvoice> queryInvoiceListByParams(Map<String, Object> params);

    /**
     * 获取开票金额
     *
     * @param  merchantId 商户ID
     * @param  storeId 店铺ID
     * @param  beginTime 开始时间
     * @param  endTime 结束时间
     * @return
     */
    BigDecimal getInvoiceTotalAmount(Integer merchantId, Integer storeId, Date beginTime, Date endTime);

}
