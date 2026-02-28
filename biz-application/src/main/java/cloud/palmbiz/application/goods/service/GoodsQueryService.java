package cloud.palmbiz.application.goods.service;

import cloud.palmbiz.application.goods.query.GoodsPageQuery;
import cloud.palmbiz.domain.goods.model.Goods;
import cloud.palmbiz.domain.goods.model.GoodsId;
import cloud.palmbiz.domain.goods.repository.GoodsRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
