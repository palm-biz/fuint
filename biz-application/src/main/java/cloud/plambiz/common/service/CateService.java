package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.GoodsCateDto;
import cloud.plambiz.common.param.GoodsCatePage;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtGoodsCate;
import java.util.List;

/**
 * 商品分类业务接口
 */
public interface CateService extends IService<MtGoodsCate> {

    /**
     * 分页查询列表
     *
     * @param catePage
     * @return
     */
    PaginationResponse<GoodsCateDto> queryCateListByPagination(GoodsCatePage catePage);

    /**
     * 添加商品分类
     *
     * @param  reqDto 分类参数
     * @throws BusinessCheckException
     * @return
     */
    MtGoodsCate addCate(MtGoodsCate reqDto) throws BusinessCheckException;

    /**
     * 根据ID获取商品分类信息
     *
     * @param  id ID
     * @return
     */
    MtGoodsCate queryCateById(Integer id);

    /**
     * 根据ID删除
     *
     * @param  id 分类ID
     * @param  operator 操作人
     * @throws BusinessCheckException
     * @return
     */
    void deleteCate(Integer id, String operator) throws BusinessCheckException;

    /**
     * 更新分类
     * @param  reqDto 分类参数
     * @throws BusinessCheckException
     * @return
     * */
    MtGoodsCate updateCate(MtGoodsCate reqDto) throws BusinessCheckException;

    /**
     * 获取分类列表
     *
     * @param merchantId 商户
     * @param storeId 店铺ID
     * @param name 店铺名称
     * @param status 状态
     * @return
     * */
    List<MtGoodsCate> getCateList(Integer merchantId, Integer storeId, String name, String status);

    /**
     * 获取分类ID
     *
     * @param merchantId 商户ID
     * @param storeId 店铺ID
     * @param name 分类名称
     * @return
     * */
    Integer getGoodsCateId(Integer merchantId, Integer storeId, String name);

}
