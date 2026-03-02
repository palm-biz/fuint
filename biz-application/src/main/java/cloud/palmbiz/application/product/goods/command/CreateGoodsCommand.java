package cloud.palmbiz.application.product.goods.command;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建商品命令
 *
 * @author DDD Refactoring
 */
@Data
public class CreateGoodsCommand {

    private String name;
    private String type;
    private Integer merchantId;
    private Integer storeId;
    private Integer cateId;
    private String goodsNo;
    private String logo;
    private String images;
    private BigDecimal price;
    private BigDecimal linePrice;
    private BigDecimal costPrice;
    private Double stock;
    private String description;
    private String salePoint;
    private Integer sort;
    private String canUsePoint;
    private String isMemberDiscount;
    private String couponIds;
    private Integer serviceTime;
    private BigDecimal weight;
    private List<SkuItem> skuList;

    @Data
    public static class SkuItem {
        private String skuNo;
        private String specIds;
        private BigDecimal price;
        private BigDecimal linePrice;
        private BigDecimal costPrice;
        private Double stock;
        private BigDecimal weight;
    }
}
