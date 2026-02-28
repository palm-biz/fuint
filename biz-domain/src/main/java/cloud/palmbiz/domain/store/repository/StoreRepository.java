package cloud.palmbiz.domain.store.repository;

import cloud.palmbiz.domain.store.model.Store;
import cloud.palmbiz.domain.store.model.StoreId;

import java.util.List;
import java.util.Map;

/**
 * 店铺仓储接口
 */
public interface StoreRepository {

    /**
     * 根据ID查找店铺
     */
    Store findById(StoreId storeId);

    /**
     * 根据名称查找店铺
     */
    Store findByName(String storeName);

    /**
     * 根据商户号获取默认店铺
     */
    Store findDefaultByMerchantNo(String merchantNo);

    /**
     * 根据参数查询店铺列表
     */
    List<Store> findByParams(Map<String, Object> params);

    /**
     * 根据距离查询店铺列表
     */
    List<Store> findByDistance(Integer merchantId, String keyword, String latitude, String longitude);

    /**
     * 获取我的店铺列表
     */
    List<Store> findMyStores(Integer merchantId, Integer storeId, String status);

    /**
     * 保存店铺
     */
    void save(Store store);

    /**
     * 移除店铺
     */
    void remove(Store store);

    /**
     * 重置默认店铺
     */
    void resetDefaultStore(Integer merchantId);

    /**
     * 根据商户ID删除店铺
     */
    void removeByMerchantId(Integer merchantId);

    /**
     * 删除店铺商品关联
     */
    void removeStoreGoods(Integer storeId);
}
