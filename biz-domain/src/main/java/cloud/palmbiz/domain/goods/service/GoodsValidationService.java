package cloud.palmbiz.domain.goods.service;

import cloud.palmbiz.domain.goods.model.Goods;
import cloud.palmbiz.domain.goods.model.GoodsSku;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 商品验证领域服务
 * 负责商品相关的业务规则验证
 *
 * @author DDD Refactoring
 */
@Service
public class GoodsValidationService {

    /**
     * 验证商品是否可以创建
     */
    public void validateGoodsCreation(String name, BigDecimal price, Double stock) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("商品名称不能为空");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("商品价格不能为负数");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("商品库存不能为负数");
        }
    }

    /**
     * 验证商品是否可以售卖
     */
    public void validateGoodsSale(Goods goods, Double quantity) {
        if (goods == null) {
            throw new IllegalArgumentException("商品不存在");
        }
        if (goods.isDeleted()) {
            throw new IllegalStateException("商品已删除，不能售卖");
        }
        if (!goods.isAvailable()) {
            throw new IllegalStateException("商品已下架，不能售卖");
        }
        if (quantity != null && quantity > 0 && !goods.isStockSufficient(quantity)) {
            throw new IllegalStateException("商品库存不足");
        }
    }

    /**
     * 验证SKU是否可以售卖
     */
    public void validateSkuSale(GoodsSku sku, Double quantity) {
        if (sku == null) {
            throw new IllegalArgumentException("SKU不存在");
        }
        if (quantity != null && quantity > 0 && !sku.isStockSufficient(quantity)) {
            throw new IllegalStateException("SKU库存不足");
        }
    }

    /**
     * 验证库存扣减
     */
    public void validateStockDeduction(Double currentStock, Double deductAmount) {
        if (currentStock == null || currentStock < 0) {
            throw new IllegalArgumentException("当前库存不合法");
        }
        if (deductAmount == null || deductAmount <= 0) {
            throw new IllegalArgumentException("扣减数量必须大于0");
        }
        if (currentStock < deductAmount) {
            throw new IllegalStateException("库存不足，无法扣减");
        }
    }

    /**
     * 验证价格更新
     */
    public void validatePriceUpdate(BigDecimal newPrice, BigDecimal costPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("新价格不能为负数");
        }
        if (costPrice != null && costPrice.compareTo(BigDecimal.ZERO) > 0
                && newPrice.compareTo(costPrice) < 0) {
            throw new IllegalArgumentException("售价不能低于成本价");
        }
    }

    /**
     * 验证商品状态变更
     */
    public void validateStatusChange(Goods goods, String targetStatus) {
        if (goods == null) {
            throw new IllegalArgumentException("商品不存在");
        }
        if (goods.isDeleted()) {
            throw new IllegalStateException("已删除的商品不能变更状态");
        }
    }

    /**
     * 验证商品编码唯一性
     */
    public void validateGoodsNoUniqueness(String goodsNo, boolean exists) {
        if (goodsNo == null || goodsNo.trim().isEmpty()) {
            throw new IllegalArgumentException("商品编码不能为空");
        }
        if (exists) {
            throw new IllegalStateException("商品编码已存在：" + goodsNo);
        }
    }

    /**
     * 验证商品信息
     */
    public void validateGoodsInfo(String name, String type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("商品名称不能为空");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("商品名称不能超过100个字符");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("商品类型不能为空");
        }
    }

    /**
     * 验证重量
     */
    public void validateWeight(BigDecimal weight) {
        if (weight != null && weight.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("商品重量不能为负数");
        }
    }

    /**
     * 验证服务时长
     */
    public void validateServiceTime(Integer serviceTime) {
        if (serviceTime != null && serviceTime < 0) {
            throw new IllegalArgumentException("服务时长不能为负数");
        }
    }

    /**
     * 验证商品所属
     */
    public void validateGoodsOwnership(Goods goods, Integer merchantId) {
        if (goods == null) {
            throw new IllegalArgumentException("商品不存在");
        }
        if (merchantId == null) {
            throw new IllegalArgumentException("商户ID不能为空");
        }
        if (!merchantId.equals(goods.getMerchantId())) {
            throw new IllegalStateException("商品不属于当前商户");
        }
    }
}
