package cloud.palmbiz.domain.goods.repository;

import cloud.palmbiz.domain.goods.model.Goods;
import cloud.palmbiz.domain.goods.model.GoodsId;

import java.util.List;
import java.util.Map;

/**
 * 商品仓储接口
 * 定义商品聚合的持久化操作
 *
 * @author DDD Refactoring
 */
public interface GoodsRepository {

    /**
     * 根据ID查找商品
     */
    Goods findById(GoodsId goodsId);

    /**
     * 根据商品编码查找商品
     */
    Goods findByGoodsNo(Integer merchantId, String goodsNo);

    /**
     * 保存商品（新增或更新）
     */
    void save(Goods goods);

    /**
     * 删除商品
     */
    void remove(Goods goods);

    /**
     * 根据商户ID查找商品列表
     */
    List<Goods> findByMerchantId(Integer merchantId);

    /**
     * 根据店铺ID查找商品列表
     */
    List<Goods> findByStoreId(Integer storeId);

    /**
     * 根据分类ID查找商品列表
     */
    List<Goods> findByCateId(Integer cateId);

    /**
     * 分页查询商品
     */
    List<Goods> findByPage(Map<String, Object> params, Integer pageNumber, Integer pageSize);

    /**
     * 统计商品数量
     */
    Long countByCondition(Map<String, Object> params);

    /**
     * 根据状态查找商品
     */
    List<Goods> findByStatus(Integer merchantId, String status);

    /**
     * 搜索商品（按名称或编码）
     */
    List<Goods> searchGoods(Integer merchantId, String keyword);

    /**
     * 获取热销商品
     */
    List<Goods> findHotGoods(Integer merchantId, Integer limit);

    /**
     * 获取推荐商品
     */
    List<Goods> findRecommendGoods(Integer merchantId, Integer limit);

    /**
     * 获取库存不足的商品
     */
    List<Goods> findLowStockGoods(Integer merchantId, Double threshold);

    /**
     * 批量更新状态
     */
    void batchUpdateStatus(List<Integer> goodsIds, String status);

    /**
     * 检查商品编码是否存在
     */
    boolean existsByGoodsNo(Integer merchantId, String goodsNo);

    /**
     * 获取指定类型的商品
     */
    List<Goods> findByType(Integer merchantId, String type);

    /**
     * 根据卡券ID查找关联商品
     */
    List<Goods> findByCouponId(Integer couponId);
}
