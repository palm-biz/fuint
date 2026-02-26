package cloud.palmbiz.repository.mapper;

import cloud.palmbiz.repository.model.MtUserGrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 */
public interface MtUserGradeMapper extends BaseMapper<MtUserGrade> {

    List<MtUserGrade> getMerchantGradeList(@Param("merchantId") Integer merchantId, @Param("status") String status);

}
