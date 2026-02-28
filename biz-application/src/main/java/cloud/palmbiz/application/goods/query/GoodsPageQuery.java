package cloud.palmbiz.application.goods.query;

import lombok.Data;

/**
 * 商品分页查询
 *
 * @author DDD Refactoring
 */
@Data
public class GoodsPageQuery {

    private Integer merchantId;
    private Integer storeId;
    private Integer cateId;
    private String type;
    private String status;
    private String keyword;
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
}
