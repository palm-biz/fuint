package cloud.palmbiz.domain.cart.repository;

import cloud.palmbiz.domain.cart.model.CartId;
import cloud.palmbiz.domain.cart.model.CartItem;

import java.util.List;
import java.util.Map;

/**
 * 购物车仓储接口
 * 定义购物车的持久化操作
 *
 * @author DDD Refactoring
 */
public interface CartRepository {

    /**
     * 根据ID查找购物车项
     */
    CartItem findById(CartId cartId);

    /**
     * 保存购物车项（新增或更新）
     */
    void save(CartItem item);

    /**
     * 删除购物车项
     */
    void remove(CartItem item);

    /**
     * 根据会员ID查找购物车
     */
    List<CartItem> findByUserId(Integer userId);

    /**
     * 根据挂单号查找购物车
     */
    List<CartItem> findByHangNo(String hangNo);

    /**
     * 查找会员的指定商品
     */
    CartItem findByUserIdAndGoods(Integer userId, Integer goodsId, Integer skuId);

    /**
     * 根据条件查询
     */
    List<CartItem> findByParams(Map<String, Object> params);

    /**
     * 清空会员购物车
     */
    void clearByUserId(Integer userId);

    /**
     * 删除指定挂单号的购物车
     */
    void removeByHangNo(String hangNo);

    /**
     * 批量删除
     */
    void batchRemove(List<Integer> cartIds);

    /**
     * 统计会员购物车商品数量
     */
    Long countByUserId(Integer userId);
}
