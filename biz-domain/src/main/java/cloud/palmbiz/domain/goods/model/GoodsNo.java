package cloud.palmbiz.domain.goods.model;

import lombok.Value;
import org.apache.commons.lang.StringUtils;

/**
 * 商品编码值对象
 *
 * @author DDD Refactoring
 */
@Value
public class GoodsNo {

    String value;

    private GoodsNo(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("商品编码不能为空");
        }
        this.value = value;
    }

    public static GoodsNo of(String value) {
        return new GoodsNo(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
