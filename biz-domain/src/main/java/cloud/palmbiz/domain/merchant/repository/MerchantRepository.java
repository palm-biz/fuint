package cloud.palmbiz.domain.merchant.repository;

import cloud.palmbiz.domain.merchant.model.Merchant;
import cloud.palmbiz.domain.merchant.model.MerchantId;

import java.util.List;
import java.util.Map;

/**
 * 商户仓储接口
 */
public interface MerchantRepository {

    /**
     * 根据ID查找商户
     */
    Merchant findById(MerchantId merchantId);

    /**
     * 根据名称查找商户
     */
    Merchant findByName(String name);

    /**
     * 根据商户号查找商户
     */
    Merchant findByNo(String merchantNo);

    /**
     * 根据参数查询商户列表
     */
    List<Merchant> findByParams(Map<String, Object> params);

    /**
     * 获取我的商户列表
     */
    List<Merchant> findMyMerchants(Integer merchantId, Integer storeId, String status);

    /**
     * 保存商户
     */
    void save(Merchant merchant);

    /**
     * 移除商户
     */
    void remove(Merchant merchant);
}
