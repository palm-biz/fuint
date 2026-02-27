package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.MtUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员分组 Mapper 接口
 */
public interface MtUserGroupMapper extends BaseMapper<MtUserGroup> {
    Long getMemberNum(@Param("groupIds") List<Integer> groupIds);
}