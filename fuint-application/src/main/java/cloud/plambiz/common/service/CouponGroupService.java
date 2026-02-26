package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.ReqCouponGroupDto;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationRequest;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtCouponGroup;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

/**
 * 卡券分组业务接口
 */
public interface CouponGroupService extends IService<MtCouponGroup> {

    /**
     * 分页查询分组列表
     *
     * @param paginationRequest
     * @return
     */
    PaginationResponse<MtCouponGroup> queryCouponGroupListByPagination(PaginationRequest paginationRequest);

    /**
     * 添加卡券分组
     *
     * @param reqCouponGroupDto
     * @return
     */
    MtCouponGroup addCouponGroup(ReqCouponGroupDto reqCouponGroupDto);

    /**
     * 修改卡券分组
     *
     * @param reqCouponGroupDto
     * @throws BusinessCheckException
     * @return
     */
    MtCouponGroup updateCouponGroup(ReqCouponGroupDto reqCouponGroupDto) throws BusinessCheckException;

    /**
     * 根据组ID获取分组信息
     *
     * @param id 分组ID
     * @return
     */
    MtCouponGroup queryCouponGroupById(Integer id);

    /**
     * 根据分组ID 删除分组信息
     *
     * @param id       分组ID
     * @param operator 操作人
     * @return
     */
    void deleteCouponGroup(Integer id, String operator);

    /**
     * 根据分组ID 获取券种类数量
     *
     * @param id       分组ID
     * @return
     */
    Integer getCouponNum(Integer id);

    /**
     * 根据分组ID 获取券总价值
     *
     * @param id 分组ID
     * @return
     */
    BigDecimal getCouponMoney(Integer id);

    /**
     * 获取已发放套数
     *
     * @param  id  分组ID
     * @return
     * */
    Integer getSendNum(Integer id);

    /**
     * 导入发券列表
     *
     * @param file excel文件
     * @param operator 操作者
     * */
    String importSendCoupon(MultipartFile file, String operator, String filePath) throws BusinessCheckException;

}
