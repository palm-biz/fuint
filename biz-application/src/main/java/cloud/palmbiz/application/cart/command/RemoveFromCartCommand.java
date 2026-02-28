package cloud.palmbiz.application.cart.command;

import lombok.Data;

@Data
public class RemoveFromCartCommand {
    private String cartIds;
    private Integer userId;
}
