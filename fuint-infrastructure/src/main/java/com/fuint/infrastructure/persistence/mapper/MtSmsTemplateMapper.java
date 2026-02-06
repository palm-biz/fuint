package com.fuint.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fuint.infrastructure.persistence.entity.MtSmsTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MtSmsTemplate Mapper
 *
 * @author fuint
 * @since 2.0.0
 */
@Mapper
public interface MtSmsTemplateMapper extends BaseMapper<MtSmsTemplateDO> {

    // MyBatis Plus 提供基础 CRUD,无需额外定义
    // 如需自定义 SQL,可在 mapper/MtSmsTemplateMapper.xml 中定义
}
