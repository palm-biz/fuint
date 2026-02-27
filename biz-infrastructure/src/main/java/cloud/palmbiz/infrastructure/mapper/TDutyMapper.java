package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.TDuty;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 Mapper 接口
 */
public interface TDutyMapper extends BaseMapper<TDuty> {

    List<TDuty> findByStatus(@Param("merchantId") Integer merchantId, @Param("status") String status);

    List<TDuty> findByIdIn(@Param("ids") List<Integer> ids);

    TDuty findByName(@Param("merchantId") Integer merchantId, @Param("name") String name);

    List<Long> getRoleIdsByAccountId(@Param("accountId") Integer accountId);
}
