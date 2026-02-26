package cloud.plambiz.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cloud.plambiz.repository.model.MtCommissionRelation;
import org.apache.ibatis.annotations.Param;

/**
 * 会员分销关系 Mapper 接口
 */
public interface MtCommissionRelationMapper extends BaseMapper<MtCommissionRelation> {

    Integer getCommissionUserId(@Param("merchantId") Integer merchantId, @Param("userId") Integer userId);

}
