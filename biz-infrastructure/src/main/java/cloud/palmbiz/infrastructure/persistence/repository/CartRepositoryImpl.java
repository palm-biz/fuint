package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.domain.cart.model.CartId;
import cloud.palmbiz.domain.cart.model.CartItem;
import cloud.palmbiz.domain.cart.repository.CartRepository;
import cloud.palmbiz.infrastructure.mapper.MtCartMapper;
import cloud.palmbiz.infrastructure.model.MtCart;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 购物车仓储实现
 * 实现购物车的持久化操作
 *
 * @author DDD Refactoring
 */
@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final MtCartMapper cartMapper;

    @Override
    public CartItem findById(CartId cartId) {
        if (cartId == null) {
            return null;
        }
        MtCart po = cartMapper.selectById(cartId.getValue());
        return toDomain(po);
    }

    @Override
    public void save(CartItem item) {
        if (item == null) {
            return;
        }

        MtCart po = toPO(item);

        if (item.getId() == null) {
            cartMapper.insert(po);
        } else {
            cartMapper.updateById(po);
        }
    }

    @Override
    public void remove(CartItem item) {
        if (item == null || item.getId() == null) {
            return;
        }
        cartMapper.deleteById(item.getId());
    }

    @Override
    public List<CartItem> findByUserId(Integer userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCart::getUserId, userId);
        wrapper.eq(MtCart::getStatus, "A");
        wrapper.orderByDesc(MtCart::getCreateTime);

        List<MtCart> poList = cartMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartItem> findByHangNo(String hangNo) {
        if (hangNo == null || hangNo.isEmpty()) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCart::getHangNo, hangNo);
        wrapper.eq(MtCart::getStatus, "A");

        List<MtCart> poList = cartMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public CartItem findByUserIdAndGoods(Integer userId, Integer goodsId, Integer skuId) {
        if (userId == null || goodsId == null) {
            return null;
        }

        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCart::getUserId, userId);
        wrapper.eq(MtCart::getGoodsId, goodsId);
        if (skuId != null) {
            wrapper.eq(MtCart::getSkuId, skuId);
        }
        wrapper.eq(MtCart::getStatus, "A");
        wrapper.last("LIMIT 1");

        MtCart po = cartMapper.selectOne(wrapper);
        return toDomain(po);
    }

    @Override
    public List<CartItem> findByParams(Map<String, Object> params) {
        LambdaQueryWrapper<MtCart> wrapper = buildQueryWrapper(params);

        List<MtCart> poList = cartMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void clearByUserId(Integer userId) {
        if (userId == null) {
            return;
        }

        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCart::getUserId, userId);
        wrapper.eq(MtCart::getStatus, "A");

        cartMapper.delete(wrapper);
    }

    @Override
    public void removeByHangNo(String hangNo) {
        if (hangNo == null || hangNo.isEmpty()) {
            return;
        }

        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCart::getHangNo, hangNo);

        cartMapper.delete(wrapper);
    }

    @Override
    public void batchRemove(List<Integer> cartIds) {
        if (cartIds == null || cartIds.isEmpty()) {
            return;
        }
        cartMapper.deleteBatchIds(cartIds);
    }

    @Override
    public Long countByUserId(Integer userId) {
        if (userId == null) {
            return 0L;
        }

        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCart::getUserId, userId);
        wrapper.eq(MtCart::getStatus, "A");

        return cartMapper.selectCount(wrapper);
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 领域对象转PO
     */
    private MtCart toPO(CartItem domain) {
        if (domain == null) {
            return null;
        }

        MtCart po = new MtCart();
        po.setId(domain.getId());
        po.setUserId(domain.getUserId());
        po.setMerchantId(domain.getMerchantId());
        po.setStoreId(domain.getStoreId());
        po.setIsVisitor(domain.getIsVisitor());
        po.setHangNo(domain.getHangNo());
        po.setSkuId(domain.getSkuId());
        po.setGoodsId(domain.getGoodsId());
        po.setNum(domain.getQuantityValue());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        po.setStatus(domain.getStatusCode());

        return po;
    }

    /**
     * PO转领域对象
     */
    private CartItem toDomain(MtCart po) {
        if (po == null) {
            return null;
        }

        return CartItem.reconstitute(
                po.getId(),
                po.getUserId(),
                po.getMerchantId(),
                po.getStoreId(),
                po.getIsVisitor(),
                po.getHangNo(),
                po.getSkuId(),
                po.getGoodsId(),
                po.getNum(),
                po.getCreateTime(),
                po.getUpdateTime(),
                po.getStatus()
        );
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<MtCart> buildQueryWrapper(Map<String, Object> params) {
        LambdaQueryWrapper<MtCart> wrapper = Wrappers.lambdaQuery();

        if (params == null || params.isEmpty()) {
            return wrapper;
        }

        if (params.containsKey("userId")) {
            wrapper.eq(MtCart::getUserId, params.get("userId"));
        }
        if (params.containsKey("merchantId")) {
            wrapper.eq(MtCart::getMerchantId, params.get("merchantId"));
        }
        if (params.containsKey("storeId")) {
            wrapper.eq(MtCart::getStoreId, params.get("storeId"));
        }
        if (params.containsKey("hangNo")) {
            wrapper.eq(MtCart::getHangNo, params.get("hangNo"));
        }
        if (params.containsKey("status")) {
            wrapper.eq(MtCart::getStatus, params.get("status"));
        }

        return wrapper;
    }
}
