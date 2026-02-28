package cloud.palmbiz.domain.coupon.repository;

import cloud.palmbiz.domain.coupon.model.Coupon;
import cloud.palmbiz.domain.coupon.model.CouponId;

import java.util.List;
import java.util.Map;

/**
 * 卡券仓储接口
 * 定义卡券聚合的持久化操作
 *
 * @author DDD Refactoring
 */
public interface CouponRepository {

    /**
     * 根据ID查找卡券
     */
    Coupon findById(CouponId couponId);

    /**
     * 保存卡券（新增或更新）
     */
    void save(Coupon coupon);

    /**
     * 删除卡券
     */
    void remove(Coupon coupon);

    /**
     * 根据商户ID查找卡券列表
     */
    List<Coupon> findByMerchantId(Integer merchantId);

    /**
     * 根据店铺ID查找卡券列表
     */
    List<Coupon> findByStoreId(Integer storeId);

    /**
     * 根据券组ID查找卡券
     */
    List<Coupon> findByGroupId(Integer groupId);

    /**
     * 分页查询卡券
     */
    List<Coupon> findByPage(Map<String, Object> params, Integer pageNumber, Integer pageSize);

    /**
     * 统计卡券数量
     */
    Long countByCondition(Map<String, Object> params);

    /**
     * 根据领取码查找卡券
     */
    Coupon findByReceiveCode(String receiveCode);

    /**
     * 根据类型查找卡券
     */
    List<Coupon> findByType(Integer merchantId, String type);

    /**
     * 查找可用的卡券
     */
    List<Coupon> findAvailableCoupons(Integer merchantId);

    /**
     * 批量更新状态
     */
    void batchUpdateStatus(List<Integer> couponIds, String status);
}
