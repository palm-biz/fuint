package com.fuint.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fuint.infrastructure.persistence.entity.MtVerifyCodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MtVerifyCode Mapper
 *
 * @author fuint
 * @since 2.0.0
 */
@Mapper
public interface MtVerifyCodeMapper extends BaseMapper<MtVerifyCodeDO> {

    // MyBatis Plus 提供基础 CRUD,无需额外定义
    // 如需自定义 SQL,可在 mapper/MtVerifyCodeMapper.xml 中定义
}
