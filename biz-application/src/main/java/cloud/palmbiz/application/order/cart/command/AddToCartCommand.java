package cloud.palmbiz.application.order.cart.command;

import lombok.Data;

@Data
public class AddToCartCommand {
    private Integer userId;
    private Integer merchantId;
    private Integer storeId;
    private Integer goodsId;
    private Integer skuId;
    private Double num;
    private String action; // + or - or =
}
