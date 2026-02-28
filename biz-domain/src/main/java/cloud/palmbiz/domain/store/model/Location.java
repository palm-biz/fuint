package cloud.palmbiz.domain.store.model;

import lombok.Value;

/**
 * 位置信息值对象
 */
@Value
public class Location {
    String latitude;
    String longitude;

    public static Location of(String latitude, String longitude) {
        if (latitude == null || longitude == null) {
            return null;
        }
        // 验证经纬度格式
        try {
            double lat = Double.parseDouble(latitude);
            double lng = Double.parseDouble(longitude);

            // 纬度范围：-90 到 90
            if (lat < -90 || lat > 90) {
                throw new IllegalArgumentException("纬度必须在-90到90之间");
            }
            // 经度范围：-180 到 180
            if (lng < -180 || lng > 180) {
                throw new IllegalArgumentException("经度必须在-180到180之间");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("经纬度格式不正确");
        }

        return new Location(latitude, longitude);
    }

    public boolean isValid() {
        return latitude != null && longitude != null
                && !latitude.isEmpty() && !longitude.isEmpty();
    }
}
