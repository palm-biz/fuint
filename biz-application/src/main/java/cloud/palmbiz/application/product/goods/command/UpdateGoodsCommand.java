package cloud.palmbiz.application.product.goods.command;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 更新商品命令
 *
 * @author DDD Refactoring
 */
@Data
public class UpdateGoodsCommand {

    private Integer goodsId;
    private String name;
    private String logo;
    private String images;
    private String description;
    private String salePoint;
    private Integer cateId;
    private BigDecimal price;
    private BigDecimal linePrice;
    private BigDecimal costPrice;
    private Double stock;
    private Integer sort;
    private String operator;
}
