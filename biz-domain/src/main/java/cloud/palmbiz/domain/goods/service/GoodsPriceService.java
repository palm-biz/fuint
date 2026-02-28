package cloud.palmbiz.domain.goods.service;

import cloud.palmbiz.domain.goods.model.Goods;
import cloud.palmbiz.domain.goods.model.GoodsSku;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 商品价格领域服务
 * 负责商品价格相关的计算逻辑
 *
 * @author DDD Refactoring
 */
@Service
public class GoodsPriceService {

    /**
     * 计算会员折扣价
     */
    public BigDecimal calculateMemberPrice(Goods goods, BigDecimal memberDiscount) {
        if (goods == null || !goods.hasMemberDiscount()) {
            return goods != null ? goods.getPrice() : BigDecimal.ZERO;
        }
        if (memberDiscount == null || memberDiscount.compareTo(BigDecimal.ZERO) <= 0
                || memberDiscount.compareTo(BigDecimal.ONE) >= 0) {
            return goods.getPrice();
        }
        return goods.getPrice().multiply(memberDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算SKU会员折扣价
     */
    public BigDecimal calculateSkuMemberPrice(GoodsSku sku, BigDecimal memberDiscount) {
        if (sku == null) {
            return BigDecimal.ZERO;
        }
        if (memberDiscount == null || memberDiscount.compareTo(BigDecimal.ZERO) <= 0
                || memberDiscount.compareTo(BigDecimal.ONE) >= 0) {
            return sku.getPrice();
        }
        return sku.getPrice().multiply(memberDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算批量购买价格
     */
    public BigDecimal calculateBatchPrice(BigDecimal unitPrice, Double quantity, BigDecimal batchDiscount) {
        if (unitPrice == null || quantity == null || quantity <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        if (batchDiscount != null && batchDiscount.compareTo(BigDecimal.ZERO) > 0
                && batchDiscount.compareTo(BigDecimal.ONE) < 0) {
            totalPrice = totalPrice.multiply(batchDiscount).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return totalPrice;
    }

    /**
     * 计算利润
     */
    public BigDecimal calculateProfit(Goods goods) {
        if (goods == null) {
            return BigDecimal.ZERO;
        }
        return goods.getPrice().calculateProfit();
    }

    /**
     * 计算利润率
     */
    public BigDecimal calculateProfitRate(Goods goods) {
        if (goods == null) {
            return BigDecimal.ZERO;
        }
        return goods.getPrice().calculateProfitRate();
    }

    /**
     * 验证价格是否合理
     */
    public boolean validatePrice(BigDecimal price, BigDecimal costPrice) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (costPrice != null && costPrice.compareTo(BigDecimal.ZERO) > 0) {
            // 售价不能低于成本价
            return price.compareTo(costPrice) >= 0;
        }
        return true;
    }

    /**
     * 计算折扣后价格
     */
    public BigDecimal calculateDiscountPrice(BigDecimal originalPrice, BigDecimal discountRate) {
        if (originalPrice == null || originalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (discountRate == null || discountRate.compareTo(BigDecimal.ONE) >= 0) {
            return originalPrice;
        }
        if (discountRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return originalPrice.multiply(discountRate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
