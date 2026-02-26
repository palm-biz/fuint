package cloud.palmbiz.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 预约详情请求参数
 */
@Data
public class BookDetailParam implements Serializable {

    @ApiModelProperty(value="预约ID", name="bookId")
    private Integer bookId;

}
