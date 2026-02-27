package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.MtMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  消息 Mapper 接口
 */
public interface MtMessageMapper extends BaseMapper<MtMessage> {

    List<MtMessage> findNewMessage(@Param("userId") Integer userId, @Param("type") String type);

    List<MtMessage> findNeedSendMessage(@Param("type") String type);
}
