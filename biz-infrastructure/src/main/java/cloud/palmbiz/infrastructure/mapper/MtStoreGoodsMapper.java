package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.model.MtStoreGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 店铺商品 Mapper 接口
 */
public interface MtStoreGoodsMapper extends BaseMapper<MtStoreGoods> {

    void removeStoreGoods(@Param("storeId") Integer storeId);

}
