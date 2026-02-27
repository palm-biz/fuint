package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.MtBookItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  预约订单 Mapper 接口
 */
public interface MtBookItemMapper extends BaseMapper<MtBookItem> {

    List<String> getBookList(@Param("bookId") Integer bookId, @Param("date") String date, @Param("time") String time);

}
