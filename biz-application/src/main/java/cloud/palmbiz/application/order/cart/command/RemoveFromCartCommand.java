package cloud.palmbiz.application.order.cart.command;

import lombok.Data;

@Data
public class RemoveFromCartCommand {
    private String cartIds;
    private Integer userId;
}
