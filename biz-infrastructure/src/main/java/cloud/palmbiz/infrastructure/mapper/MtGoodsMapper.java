package cloud.palmbiz.infrastructure.mapper;

import cloud.palmbiz.infrastructure.bean.GoodsBean;
import cloud.palmbiz.infrastructure.bean.GoodsTopBean;
import cloud.palmbiz.infrastructure.model.MtGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *  商品 Mapper 接口
 */
public interface MtGoodsMapper extends BaseMapper<MtGoods> {

    List<MtGoods> getStoreGoodsList(@Param("merchantId") Integer merchantId, @Param("storeId") Integer storeId, @Param("cateId") Integer cateId, @Param("platform") String platform);

    List<MtGoods> searchStoreGoodsList(@Param("merchantId") Integer merchantId, @Param("storeId") Integer storeId, @Param("keyword") String keyword, @Param("platform") String platform);

    MtGoods getByGoodsNo(@Param("merchantId") Integer merchantId, @Param("goodsNo") String goodsNo);

    List<MtGoods> getByGoodsName(@Param("merchantId") Integer merchantId, @Param("goodsName") String goodsName);

    Boolean updateInitSale(@Param("goodsId") Integer goodsId, @Param("saleNum") Double saleNum);

    List<GoodsBean> selectGoodsList(@Param("merchantId") Integer merchantId, @Param("storeId") Integer storeId, @Param("cateId") Integer cateId, @Param("keyword") String keyword);

    List<GoodsTopBean> getGoodsSaleTopList(@Param("merchantId") Integer merchantId, @Param("storeId") Integer storeId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    void removeMerchantGoods(@Param("merchantId") Integer merchantId);

}
