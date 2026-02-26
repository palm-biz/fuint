package cloud.plambiz.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cloud.plambiz.repository.model.MtCommissionLog;
import org.apache.ibatis.annotations.Param;

/**
 *  佣金记录 Mapper 接口
 */
public interface MtCommissionLogMapper extends BaseMapper<MtCommissionLog> {

    Boolean confirmCommissionLog(@Param("merchantId") Integer merchantId, @Param("uuid") String uuid, @Param("operator") String operator);

    Boolean cancelCommissionLog(@Param("merchantId") Integer merchantId, @Param("uuid") String uuid, @Param("operator") String operator);

}
