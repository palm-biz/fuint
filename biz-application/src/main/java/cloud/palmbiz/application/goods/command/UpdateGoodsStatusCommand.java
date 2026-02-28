package cloud.palmbiz.application.goods.command;

import lombok.Data;

/**
 * 更新商品状态命令
 *
 * @author DDD Refactoring
 */
@Data
public class UpdateGoodsStatusCommand {

    private Integer goodsId;
    private String status;
    private String operator;
}
