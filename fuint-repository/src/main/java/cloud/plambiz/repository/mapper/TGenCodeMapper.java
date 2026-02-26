package cloud.plambiz.repository.mapper;

import cloud.plambiz.repository.bean.ColumnBean;
import cloud.plambiz.repository.model.TGenCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 代码生成 Mapper 接口
 */
public interface TGenCodeMapper extends BaseMapper<TGenCode> {

    TGenCode findGenCodeByTableName(@Param("tableName") String tableName);

    List<ColumnBean> getTableColumnList(@Param("tableName") String tableName);

}
