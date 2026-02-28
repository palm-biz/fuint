package cloud.palmbiz.application.store.query;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 店铺查询对象
 */
@Data
public class StoreQuery {
    private Integer storeId;
    private Integer merchantId;
    private String name;
    private String status;
    private String isDefault;

    public Map<String, Object> toParams() {
        Map<String, Object> params = new HashMap<>();
        if (storeId != null) params.put("storeId", storeId);
        if (merchantId != null) params.put("merchantId", merchantId);
        if (name != null) params.put("name", name);
        if (status != null) params.put("status", status);
        if (isDefault != null) params.put("is_default", isDefault);
        return params;
    }
}
