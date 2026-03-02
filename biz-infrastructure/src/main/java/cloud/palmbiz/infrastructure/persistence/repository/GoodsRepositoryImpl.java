package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.domain.goods.model.*;
import cloud.palmbiz.domain.goods.repository.GoodsRepository;
import cloud.palmbiz.infrastructure.mapper.MtGoodsMapper;
import cloud.palmbiz.infrastructure.mapper.MtGoodsSkuMapper;
import cloud.palmbiz.infrastructure.model.MtGoods;
import cloud.palmbiz.infrastructure.model.MtGoodsSku;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品仓储实现
 * 实现商品聚合的持久化操作
 *
 * @author DDD Refactoring
 */
@Repository
@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepository {

    private final MtGoodsMapper goodsMapper;
    private final MtGoodsSkuMapper skuMapper;

    @Override
    public Goods findById(GoodsId goodsId) {
        if (goodsId == null) {
            return null;
        }
        MtGoods po = goodsMapper.selectById(goodsId.getValue());
        return toDomain(po);
    }

    @Override
    public Goods findByGoodsNo(Integer merchantId, String goodsNo) {
        if (goodsNo == null || goodsNo.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoods::getGoodsNo, goodsNo);
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        wrapper.last("LIMIT 1");
        MtGoods po = goodsMapper.selectOne(wrapper);
        return toDomain(po);
    }

    @Override
    public void save(Goods goods) {
        if (goods == null) {
            return;
        }

        MtGoods po = toPO(goods);

        if (goods.getId() == null) {
            goodsMapper.insert(po);
        } else {
            goodsMapper.updateById(po);
        }

        // 保存SKU列表
        if (goods.getSkuList() != null && !goods.getSkuList().isEmpty()) {
            saveSkuList(goods.getId(), goods.getSkuList());
        }
    }

    @Override
    public void remove(Goods goods) {
        if (goods == null || goods.getId() == null) {
            return;
        }
        goodsMapper.deleteById(goods.getId());

        // 删除SKU
        LambdaQueryWrapper<MtGoodsSku> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoodsSku::getGoodsId, goods.getId());
        skuMapper.delete(wrapper);
    }

    @Override
    public List<Goods> findByMerchantId(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoods::getMerchantId, merchantId);
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.orderByDesc(MtGoods::getCreateTime);

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findByStoreId(Integer storeId) {
        if (storeId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoods::getStoreId, storeId);
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.orderByDesc(MtGoods::getCreateTime);

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findByCateId(Integer cateId) {
        if (cateId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoods::getCateId, cateId);
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.orderByAsc(MtGoods::getSort);

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findByPage(Map<String, Object> params, Integer pageNumber, Integer pageSize) {
        return Collections.emptyList();
    }

    @Override
    public Long countByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<MtGoods> wrapper = buildQueryWrapper(params);
        return goodsMapper.selectCount(wrapper);
    }

    @Override
    public List<Goods> findByStatus(Integer merchantId, String status) {
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        if (status != null && !status.isEmpty()) {
            wrapper.eq(MtGoods::getStatus, status);
        }
        wrapper.orderByDesc(MtGoods::getCreateTime);

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> searchGoods(Integer merchantId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        wrapper.and(w -> w.like(MtGoods::getName, keyword).or().like(MtGoods::getGoodsNo, keyword));
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.orderByDesc(MtGoods::getCreateTime);

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findHotGoods(Integer merchantId, Integer limit) {
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.orderByDesc(MtGoods::getInitSale);
        wrapper.last("LIMIT " + (limit != null ? limit : 10));

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findRecommendGoods(Integer merchantId, Integer limit) {
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.orderByDesc(MtGoods::getSort);
        wrapper.last("LIMIT " + (limit != null ? limit : 10));

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findLowStockGoods(Integer merchantId, Double threshold) {
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        wrapper.eq(MtGoods::getStatus, "A");
        wrapper.le(MtGoods::getStock, threshold != null ? threshold : 10.0);
        wrapper.orderByAsc(MtGoods::getStock);

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void batchUpdateStatus(List<Integer> goodsIds, String status) {
        if (goodsIds == null || goodsIds.isEmpty() || status == null) {
            return;
        }

        MtGoods updatePO = new MtGoods();
        updatePO.setStatus(status);
        updatePO.setUpdateTime(new Date());

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MtGoods::getId, goodsIds);

        goodsMapper.update(updatePO, wrapper);
    }

    @Override
    public boolean existsByGoodsNo(Integer merchantId, String goodsNo) {
        if (goodsNo == null || goodsNo.isEmpty()) {
            return false;
        }

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoods::getGoodsNo, goodsNo);
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }

        return goodsMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<Goods> findByType(Integer merchantId, String type) {
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtGoods::getMerchantId, merchantId);
        }
        if (type != null && !type.isEmpty()) {
            wrapper.eq(MtGoods::getType, type);
        }
        wrapper.eq(MtGoods::getStatus, "A");

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goods> findByCouponId(Integer couponId) {
        if (couponId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();
        wrapper.like(MtGoods::getCouponIds, couponId.toString());
        wrapper.eq(MtGoods::getStatus, "A");

        List<MtGoods> poList = goodsMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 领域对象转PO
     */
    private MtGoods toPO(Goods domain) {
        if (domain == null) {
            return null;
        }

        MtGoods po = new MtGoods();
        po.setId(domain.getId());
        po.setMerchantId(domain.getMerchantId());
        po.setStoreId(domain.getStoreId());
        po.setName(domain.getName());
        po.setType(domain.getType());
        po.setCateId(domain.getCateId());
        po.setBookId(domain.getBookId());
        po.setGoodsNo(domain.getGoodsNoValue());
        po.setPlatform(domain.getPlatform());
        po.setIsSingleSpec(domain.getIsSingleSpec());
        po.setLogo(domain.getLogo());
        po.setImages(domain.getImages());

        if (domain.getPrice() != null) {
            po.setPrice(domain.getPrice().getPrice());
            po.setLinePrice(domain.getPrice().getLinePrice());
            po.setCostPrice(domain.getPrice().getCostPrice());
        }

        po.setStock(domain.getStockValue());
        po.setCouponIds(domain.getCouponIds());
        po.setServiceTime(domain.getServiceTime());
        po.setWeight(domain.getWeight());
        po.setInitSale(domain.getInitSale());
        po.setSalePoint(domain.getSalePoint());
        po.setCanUsePoint(domain.getCanUsePoint());
        po.setIsMemberDiscount(domain.getIsMemberDiscount());
        po.setSort(domain.getSort());
        po.setDescription(domain.getDescription());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        po.setOperator(domain.getOperator());
        po.setStatus(domain.getStatusCode());

        return po;
    }

    /**
     * PO转领域对象
     */
    private Goods toDomain(MtGoods po) {
        if (po == null) {
            return null;
        }

        Goods goods = Goods.reconstitute(
                po.getId(),
                po.getMerchantId(),
                po.getStoreId(),
                po.getName(),
                po.getType(),
                po.getCateId(),
                po.getBookId(),
                po.getGoodsNo(),
                po.getPlatform(),
                po.getIsSingleSpec(),
                po.getLogo(),
                po.getImages(),
                po.getPrice(),
                po.getLinePrice(),
                po.getCostPrice(),
                po.getStock(),
                po.getCouponIds(),
                po.getServiceTime(),
                po.getWeight(),
                po.getInitSale(),
                po.getSalePoint(),
                po.getCanUsePoint(),
                po.getIsMemberDiscount(),
                po.getSort(),
                po.getDescription(),
                po.getCreateTime(),
                po.getUpdateTime(),
                po.getOperator(),
                po.getStatus()
        );

        // 加载SKU列表
        List<GoodsSku> skuList = loadSkuList(po.getId());
        goods.setSkuList(skuList);

        return goods;
    }

    /**
     * 加载SKU列表
     */
    private List<GoodsSku> loadSkuList(Integer goodsId) {
        if (goodsId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtGoodsSku> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtGoodsSku::getGoodsId, goodsId);
        wrapper.eq(MtGoodsSku::getStatus, "A");

        List<MtGoodsSku> poList = skuMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toSkuDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * PO转SKU领域对象
     */
    private GoodsSku toSkuDomain(MtGoodsSku po) {
        if (po == null) {
            return null;
        }

        return GoodsSku.reconstitute(
                po.getId(),
                po.getSkuNo(),
                po.getLogo(),
                po.getGoodsId(),
                po.getSpecIds(),
                po.getStock(),
                po.getPrice(),
                po.getLinePrice(),
                po.getCostPrice(),
                po.getWeight(),
                po.getStatus()
        );
    }

    /**
     * SKU领域对象转PO
     */
    private MtGoodsSku toSkuPO(GoodsSku domain) {
        if (domain == null) {
            return null;
        }

        MtGoodsSku po = new MtGoodsSku();
        po.setId(domain.getId());
        po.setSkuNo(domain.getSkuNo());
        po.setLogo(domain.getLogo());
        po.setGoodsId(domain.getGoodsId());
        po.setSpecIds(domain.getSpecIds());
        po.setStock(domain.getStockValue());
        po.setPrice(domain.getPrice());
        po.setLinePrice(domain.getPrice().getLinePrice());
        po.setCostPrice(domain.getPrice().getCostPrice());
        po.setWeight(domain.getWeight());
        po.setStatus(domain.getStatus());

        return po;
    }

    /**
     * 保存SKU列表
     */
    private void saveSkuList(Integer goodsId, List<GoodsSku> skuList) {
        if (goodsId == null || skuList == null || skuList.isEmpty()) {
            return;
        }

        for (GoodsSku sku : skuList) {
            MtGoodsSku po = toSkuPO(sku);
            if (po.getId() == null) {
                skuMapper.insert(po);
            } else {
                skuMapper.updateById(po);
            }
        }
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<MtGoods> buildQueryWrapper(Map<String, Object> params) {
        LambdaQueryWrapper<MtGoods> wrapper = Wrappers.lambdaQuery();

        if (params == null || params.isEmpty()) {
            return wrapper;
        }

        if (params.containsKey("merchantId")) {
            wrapper.eq(MtGoods::getMerchantId, params.get("merchantId"));
        }
        if (params.containsKey("storeId")) {
            wrapper.eq(MtGoods::getStoreId, params.get("storeId"));
        }
        if (params.containsKey("cateId")) {
            wrapper.eq(MtGoods::getCateId, params.get("cateId"));
        }
        if (params.containsKey("type")) {
            wrapper.eq(MtGoods::getType, params.get("type"));
        }
        if (params.containsKey("status")) {
            wrapper.eq(MtGoods::getStatus, params.get("status"));
        }

        return wrapper;
    }
}
