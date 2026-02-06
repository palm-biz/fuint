package com.fuint.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fuint.infrastructure.persistence.entity.MtSendLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MtSendLog Mapper
 *
 * @author fuint
 * @since 2.0.0
 */
@Mapper
public interface MtSendLogMapper extends BaseMapper<MtSendLogDO> {

    // MyBatis Plus 提供基础 CRUD,无需额外定义
    // 如需自定义 SQL,可在 mapper/MtSendLogMapper.xml 中定义
}
