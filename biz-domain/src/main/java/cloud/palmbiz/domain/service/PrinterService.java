package cloud.palmbiz.domain.service;

import cloud.palmbiz.common.user.dto.UserOrderDto;
import cloud.palmbiz.common.param.PrinterPage;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.model.MtPrinter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 打印机业务接口
 */
public interface PrinterService extends IService<MtPrinter> {

    /**
     * 分页查询列表
     *
     * @param printerPage
     * @return
     */
    PaginationResponse<MtPrinter> queryPrinterListByPagination(PrinterPage printerPage);

    /**
     * 添加打印机
     *
     * @param  mtPrinter
     * @throws BusinessCheckException
     * @return
     */
    MtPrinter addPrinter(MtPrinter mtPrinter) throws BusinessCheckException;

    /**
     * 打印订单
     *
     * @param orderInfo 订单信息
     * @param autoPrint 自动打印
     * @return
     */
    Boolean printOrder(UserOrderDto orderInfo, boolean autoPrint) throws Exception;

    /**
     * 根据ID获取打印机信息
     *
     * @param id ID
     * @return
     */
    MtPrinter queryPrinterById(Integer id);

    /**
     * 根据ID删除打印机
     *
     * @param id ID
     * @param operator 操作人
     * @throws BusinessCheckException
     * @return
     */
    void deletePrinter(Integer id, String operator) throws BusinessCheckException;

    /**
     * 更新打印机
     * @param  mtPrinter
     * @throws BusinessCheckException
     * @return
     */
    MtPrinter updatePrinter(MtPrinter mtPrinter) throws BusinessCheckException;

    /**
     * 根据条件搜索打印机
     *
     * @param params 查询参数
     * @return
     */
    List<MtPrinter> queryPrinterListByParams(Map<String, Object> params);
}
