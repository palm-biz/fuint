package com.fuint.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fuint.infrastructure.persistence.entity.MtBookCateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MtBookCate Mapper
 *
 * @author fuint
 * @since 2.0.0
 */
@Mapper
public interface MtBookCateMapper extends BaseMapper<MtBookCateDO> {

    // MyBatis Plus 提供基础 CRUD,无需额外定义
    // 如需自定义 SQL,可在 mapper/MtBookCateMapper.xml 中定义
}
