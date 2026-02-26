package cloud.plambiz.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.plambiz.common.dto.BannerDto;
import cloud.plambiz.common.param.BannerPage;
import cloud.plambiz.framework.exception.BusinessCheckException;
import cloud.plambiz.framework.pagination.PaginationResponse;
import cloud.plambiz.repository.model.MtBanner;

import java.util.List;
import java.util.Map;

/**
 * 焦点图业务接口
 */
public interface BannerService extends IService<MtBanner> {

    /**
     * 分页查询列表
     *
     * @param bannerPage
     * @return
     */
    PaginationResponse<MtBanner> queryBannerListByPagination(BannerPage bannerPage);

    /**
     * 添加Banner
     *
     * @param reqBannerDto
     * @throws BusinessCheckException
     * @return
     */
    MtBanner addBanner(BannerDto reqBannerDto) throws BusinessCheckException;

    /**
     * 根据ID获取Banner信息
     *
     * @param id Banner ID
     * @return
     */
    MtBanner queryBannerById(Integer id);

    /**
     * 更新焦点图
     * @param bannerDto
     * @throws BusinessCheckException
     * @return
     * */
    MtBanner updateBanner(BannerDto bannerDto) throws BusinessCheckException;

    /**
     * 根据条件搜索焦点图
     *
     * @param params 查询参数
     * @return
     * */
    List<MtBanner> queryBannerListByParams(Map<String, Object> params);
}
