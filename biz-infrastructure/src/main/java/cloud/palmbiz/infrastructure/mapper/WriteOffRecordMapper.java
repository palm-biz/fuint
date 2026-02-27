package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.WriteOffRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 核销记录表 Mapper 接口
 */
public interface WriteOffRecordMapper extends BaseMapper<WriteOffRecord> {

    Long getConfirmNum(@Param("userCouponId") Integer userCouponId);

    Long getConfirmLogCount(@Param("merchantId") Integer merchantId, @Param("storeId") Integer storeId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<WriteOffRecord> getOrderConfirmLogList(@Param("orderId") Integer orderId);

}
