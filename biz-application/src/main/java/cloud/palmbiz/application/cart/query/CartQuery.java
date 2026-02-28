package cloud.palmbiz.application.cart.query;

import lombok.Data;

import java.util.Map;

@Data
public class CartQuery {
    private Integer userId;
    private Integer merchantId;
    private Integer storeId;
    private String hangNo;

    public Map<String, Object> toParams() {
        Map<String, Object> params = new java.util.HashMap<>();
        if (userId != null) params.put("userId", userId);
        if (merchantId != null) params.put("merchantId", merchantId);
        if (storeId != null) params.put("storeId", storeId);
        if (hangNo != null) params.put("hangNo", hangNo);
        return params;
    }
}
