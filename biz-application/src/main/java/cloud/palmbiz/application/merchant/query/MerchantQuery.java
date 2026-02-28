package cloud.palmbiz.application.merchant.query;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 商户查询对象
 */
@Data
public class MerchantQuery {
    private Integer id;
    private String name;
    private String merchantNo;
    private String status;

    public Map<String, Object> toParams() {
        Map<String, Object> params = new HashMap<>();
        if (id != null) params.put("id", id);
        if (name != null) params.put("name", name);
        if (merchantNo != null) params.put("no", merchantNo);
        if (status != null) params.put("status", status);
        return params;
    }
}
