package cloud.palmbiz.domain.store.service;

import cloud.palmbiz.framework.exception.BusinessCheckException;
import org.springframework.stereotype.Service;

/**
 * 店铺验证领域服务
 */
@Service
public class StoreValidationService {

    /**
     * 验证店铺名称
     */
    public void validateStoreName(String name) throws BusinessCheckException {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessCheckException("店铺名称不能为空");
        }
        if (name.length() > 100) {
            throw new BusinessCheckException("店铺名称长度不能超过100个字符");
        }
    }

    /**
     * 验证联系电话
     */
    public void validatePhone(String phone) throws BusinessCheckException {
        if (phone == null || phone.trim().isEmpty()) {
            return;
        }
        // 简单的手机号验证
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new BusinessCheckException("联系电话格式不正确");
        }
    }

    /**
     * 验证经纬度
     */
    public void validateCoordinate(String latitude, String longitude) throws BusinessCheckException {
        if (latitude == null || longitude == null) {
            return;
        }
        try {
            double lat = Double.parseDouble(latitude);
            double lng = Double.parseDouble(longitude);

            if (lat < -90 || lat > 90) {
                throw new BusinessCheckException("纬度必须在-90到90之间");
            }
            if (lng < -180 || lng > 180) {
                throw new BusinessCheckException("经度必须在-180到180之间");
            }
        } catch (NumberFormatException e) {
            throw new BusinessCheckException("经纬度格式不正确");
        }
    }

    /**
     * 验证商户ID
     */
    public void validateMerchantId(Integer merchantId) throws BusinessCheckException {
        if (merchantId == null || merchantId <= 0) {
            throw new BusinessCheckException("商户ID不能为空");
        }
    }
}
