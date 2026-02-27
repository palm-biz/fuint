package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.MtCommissionRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员分销关系 Mapper 接口
 */
public interface MtCommissionRelationMapper extends BaseMapper<MtCommissionRelation> {

    Integer getCommissionUserId(@Param("merchantId") Integer merchantId, @Param("userId") Integer userId);

}
