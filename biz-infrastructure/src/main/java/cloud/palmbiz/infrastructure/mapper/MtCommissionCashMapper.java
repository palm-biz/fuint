package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.MtCommissionCash;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 *  提现记录 Mapper 接口
 */
public interface MtCommissionCashMapper extends BaseMapper<MtCommissionCash> {

    Boolean confirmCommissionCash(@Param("merchantId") Integer merchantId, @Param("uuid") String uuid, @Param("operator") String operator);

    Boolean cancelCommissionCash(@Param("merchantId") Integer merchantId, @Param("uuid") String uuid, @Param("operator") String operator);

}
