package cloud.palmbiz.application.product.goods.service;

import cloud.palmbiz.application.product.goods.query.GoodsPageQuery;
import cloud.palmbiz.common.dto.GoodsDto;
import cloud.palmbiz.common.dto.GoodsListParam;
import cloud.palmbiz.common.good.dto.GoodsTopDto;
import cloud.palmbiz.domain.goods.model.Goods;
import cloud.palmbiz.domain.goods.model.GoodsId;
import cloud.palmbiz.domain.goods.repository.GoodsRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.mapper.MtGoodsMapper;
import cloud.palmbiz.infrastructure.mapper.MtGoodsSkuMapper;
import cloud.palmbiz.infrastructure.model.MtGoods;
import cloud.palmbiz.infrastructure.model.MtGoodsSku;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品查询服务
 * 处理商品的查询操作
 *
 * @author DDD Refactoring
 */
@Service
@RequiredArgsConstructor
public class GoodsQueryService {

    private final GoodsRepository goodsRepository;
    private final MtGoodsMapper mtGoodsMapper;
    private final MtGoodsSkuMapper mtGoodsSkuMapper;

    /**
     * 根据ID查询商品
     */
    public Goods queryById(Integer goodsId) throws BusinessCheckException {
        if (goodsId == null) {
            throw new BusinessCheckException("商品ID不能为空");
        }

        Goods goods = goodsRepository.findById(GoodsId.of(goodsId));
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        return goods;
    }

    /**
     * 根据商品编码查询
     */
    public Goods queryByGoodsNo(Integer merchantId, String goodsNo) throws BusinessCheckException {
        if (goodsNo == null || goodsNo.isEmpty()) {
            throw new BusinessCheckException("商品编码不能为空");
        }

        Goods goods = goodsRepository.findByGoodsNo(merchantId, goodsNo);
        if (goods == null) {
            throw new BusinessCheckException("商品不存在");
        }

        return goods;
    }

    /**
     * 查询商户的商品列表
     */
    public List<Goods> queryByMerchantId(Integer merchantId) {
        return goodsRepository.findByMerchantId(merchantId);
    }

    /**
     * 查询店铺的商品列表
     */
    public List<Goods> queryByStoreId(Integer storeId) {
        return goodsRepository.findByStoreId(storeId);
    }

    /**
     * 查询分类的商品列表
     */
    public List<Goods> queryByCateId(Integer cateId) {
        return goodsRepository.findByCateId(cateId);
    }

    /**
     * 分页查询商品
     */
    public List<Goods> queryByPage(GoodsPageQuery query) {
        if (query == null) {
            query = new GoodsPageQuery();
        }

        Map<String, Object> params = new HashMap<>();
        if (query.getMerchantId() != null) {
            params.put("merchantId", query.getMerchantId());
        }
        if (query.getStoreId() != null) {
            params.put("storeId", query.getStoreId());
        }
        if (query.getCateId() != null) {
            params.put("cateId", query.getCateId());
        }
        if (query.getType() != null) {
            params.put("type", query.getType());
        }
        if (query.getStatus() != null) {
            params.put("status", query.getStatus());
        }

        return goodsRepository.findByPage(params, query.getPageNumber(), query.getPageSize());
    }

    /**
     * 搜索商品
     */
    public List<Goods> searchGoods(Integer merchantId, String keyword) {
        return goodsRepository.searchGoods(merchantId, keyword);
    }

    /**
     * 获取热销商品
     */
    public List<Goods> queryHotGoods(Integer merchantId, Integer limit) {
        return goodsRepository.findHotGoods(merchantId, limit);
    }

    /**
     * 获取推荐商品
     */
    public List<Goods> queryRecommendGoods(Integer merchantId, Integer limit) {
        return goodsRepository.findRecommendGoods(merchantId, limit);
    }

    /**
     * 获取库存不足的商品
     */
    public List<Goods> queryLowStockGoods(Integer merchantId, Double threshold) {
        return goodsRepository.findLowStockGoods(merchantId, threshold);
    }

    /**
     * 查询指定类型的商品
     */
    public List<Goods> queryByType(Integer merchantId, String type) {
        return goodsRepository.findByType(merchantId, type);
    }

    /**
     * 根据卡券ID查找关联商品
     */
    public List<Goods> queryByCouponId(Integer couponId) {
        return goodsRepository.findByCouponId(couponId);
    }

    /**
     * 统计商品数量
     */
    public Long countByCondition(Map<String, Object> params) {
        return goodsRepository.countByCondition(params);
    }

    /**
     * 分页查询商品列表（兼容旧接口）
     */
    public PaginationResponse<GoodsDto> queryGoodsListByPagination(GoodsListParam param) {
        return goodsRepository.queryGoodsListByPagination(param);
    }

    /**
     * 根据ID查询商品PO（兼容旧接口）
     */
    public MtGoods queryGoodsPOById(Integer id) {
        return mtGoodsMapper.selectById(id);
    }

    /**
     * 根据商品编码查询商品PO（兼容旧接口）
     */
    public MtGoods queryGoodsPOByGoodsNo(Integer merchantId, String goodsNo) {
        QueryWrapper<MtGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId).eq("goods_no", goodsNo).last("LIMIT 1");
        return mtGoodsMapper.selectOne(wrapper);
    }

    /**
     * 根据SKU编码查询SKU信息（兼容旧接口）
     */
    public MtGoodsSku getSkuInfoBySkuNo(String skuNo) {
        QueryWrapper<MtGoodsSku> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_no", skuNo).last("LIMIT 1");
        return mtGoodsSkuMapper.selectOne(wrapper);
    }

    /**
     * 获取商品详情DTO（兼容旧接口）
     */
    public GoodsDto getGoodsDetail(Integer id, boolean getDeleteSpec) {
        return goodsRepository.getGoodsDetail(id, getDeleteSpec);
    }

    /**
     * 获取店铺商品列表（兼容旧接口）
     */
    public Map<String, Object> getStoreGoodsList(Integer storeId, String keyword, String platform, Integer cateId, Integer page, Integer pageSize) {
        return goodsRepository.getStoreGoodsList(storeId, keyword, platform, cateId, page, pageSize);
    }

    /**
     * 获取商品销售排行榜（兼容旧接口）
     */
    public List<GoodsTopDto> getGoodsSaleTopList(Integer merchantId, Integer storeId, Date startTime, Date endTime) {
        return goodsRepository.getGoodsSaleTopList(merchantId, storeId, startTime, endTime);
    }
}
