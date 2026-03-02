package cloud.palmbiz.infrastructure.persistence.repository;

import cloud.palmbiz.domain.coupon.model.Coupon;
import cloud.palmbiz.domain.coupon.model.CouponId;
import cloud.palmbiz.domain.coupon.repository.CouponRepository;
import cloud.palmbiz.infrastructure.mapper.MtCouponMapper;
import cloud.palmbiz.infrastructure.model.MtCoupon;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 卡券仓储实现
 * 实现卡券聚合的持久化操作
 *
 * @author DDD Refactoring
 */
@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final MtCouponMapper couponMapper;

    @Override
    public Coupon findById(CouponId couponId) {
        if (couponId == null) {
            return null;
        }
        MtCoupon po = couponMapper.selectById(couponId.getValue());
        return toDomain(po);
    }

    @Override
    public void save(Coupon coupon) {
        if (coupon == null) {
            return;
        }

        MtCoupon po = toPO(coupon);

        if (coupon.getId() == null) {
            couponMapper.insert(po);
        } else {
            couponMapper.updateById(po);
        }
    }

    @Override
    public void remove(Coupon coupon) {
        if (coupon == null || coupon.getId() == null) {
            return;
        }
        couponMapper.deleteById(coupon.getId());
    }

    @Override
    public List<Coupon> findByMerchantId(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCoupon::getMerchantId, merchantId);
        wrapper.eq(MtCoupon::getStatus, "A");
        wrapper.orderByDesc(MtCoupon::getCreateTime);

        List<MtCoupon> poList = couponMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Coupon> findByStoreId(Integer storeId) {
        if (storeId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCoupon::getStoreId, storeId);
        wrapper.eq(MtCoupon::getStatus, "A");
        wrapper.orderByDesc(MtCoupon::getCreateTime);

        List<MtCoupon> poList = couponMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Coupon> findByGroupId(Integer groupId) {
        if (groupId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCoupon::getGroupId, groupId);
        wrapper.eq(MtCoupon::getStatus, "A");

        List<MtCoupon> poList = couponMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Coupon> findByPage(Map<String, Object> params, Integer pageNumber, Integer pageSize) {
        return Collections.emptyList();
    }

    @Override
    public Long countByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<MtCoupon> wrapper = buildQueryWrapper(params);
        return couponMapper.selectCount(wrapper);
    }

    @Override
    public Coupon findByReceiveCode(String receiveCode) {
        if (receiveCode == null || receiveCode.isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCoupon::getReceiveCode, receiveCode);
        wrapper.eq(MtCoupon::getStatus, "A");
        wrapper.last("LIMIT 1");

        MtCoupon po = couponMapper.selectOne(wrapper);
        return toDomain(po);
    }

    @Override
    public List<Coupon> findByType(Integer merchantId, String type) {
        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        if (merchantId != null) {
            wrapper.eq(MtCoupon::getMerchantId, merchantId);
        }
        if (type != null && !type.isEmpty()) {
            wrapper.eq(MtCoupon::getType, type);
        }
        wrapper.eq(MtCoupon::getStatus, "A");

        List<MtCoupon> poList = couponMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Coupon> findAvailableCoupons(Integer merchantId) {
        if (merchantId == null) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MtCoupon::getMerchantId, merchantId);
        wrapper.eq(MtCoupon::getStatus, "A");
        Date now = new Date();
        wrapper.le(MtCoupon::getBeginTime, now);
        wrapper.ge(MtCoupon::getEndTime, now);

        List<MtCoupon> poList = couponMapper.selectList(wrapper);
        return poList.stream()
                .map(this::toDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void batchUpdateStatus(List<Integer> couponIds, String status) {
        if (couponIds == null || couponIds.isEmpty() || status == null) {
            return;
        }

        MtCoupon updatePO = new MtCoupon();
        updatePO.setStatus(status);
        updatePO.setUpdateTime(new Date());

        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MtCoupon::getId, couponIds);

        couponMapper.update(updatePO, wrapper);
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 领域对象转PO
     */
    private MtCoupon toPO(Coupon domain) {
        if (domain == null) {
            return null;
        }

        MtCoupon po = new MtCoupon();
        po.setId(domain.getId());
        po.setGroupId(domain.getGroupId());
        po.setMerchantId(domain.getMerchantId());
        po.setStoreId(domain.getStoreId());
        po.setType(domain.getTypeCode());
        po.setContent(domain.getContent());
        po.setName(domain.getName());
        po.setIsGive(domain.getIsGive());
        po.setPoint(domain.getPoint());
        po.setApplyGoods(domain.getApplyGoods());
        po.setReceiveCode(domain.getReceiveCode());
        po.setUseFor(domain.getUseFor());
        po.setExpireType(domain.getExpireType());
        po.setExpireTime(domain.getExpireTime());
        po.setBeginTime(domain.getBeginTime());
        po.setEndTime(domain.getEndTime());
        po.setAmount(domain.getAmountValue());
        po.setSendWay(domain.getSendWay());
        po.setSendNum(domain.getSendNum());
        po.setTotal(domain.getTotal());
        po.setLimitNum(domain.getLimitNum());
        po.setExceptTime(domain.getExceptTime());
        po.setStoreIds(domain.getStoreIds());
        po.setGradeIds(domain.getGradeIds());
        po.setDescription(domain.getDescription());
        po.setImage(domain.getImage());
        po.setRemarks(domain.getRemarks());
        po.setInRule(domain.getInRule());
        po.setOutRule(domain.getOutRule());
        po.setCreateTime(domain.getCreateTime());
        po.setUpdateTime(domain.getUpdateTime());
        po.setOperator(domain.getOperator());
        po.setStatus(domain.getStatusCode());

        return po;
    }

    /**
     * PO转领域对象
     */
    private Coupon toDomain(MtCoupon po) {
        if (po == null) {
            return null;
        }

        return Coupon.reconstitute(
                po.getId(),
                po.getGroupId(),
                po.getMerchantId(),
                po.getStoreId(),
                po.getType(),
                po.getContent(),
                po.getName(),
                po.getIsGive(),
                po.getPoint(),
                po.getApplyGoods(),
                po.getReceiveCode(),
                po.getUseFor(),
                po.getExpireType(),
                po.getExpireTime(),
                po.getBeginTime(),
                po.getEndTime(),
                po.getAmount(),
                po.getSendWay(),
                po.getSendNum(),
                po.getTotal(),
                po.getLimitNum(),
                po.getExceptTime(),
                po.getStoreIds(),
                po.getGradeIds(),
                po.getDescription(),
                po.getImage(),
                po.getRemarks(),
                po.getInRule(),
                po.getOutRule(),
                po.getCreateTime(),
                po.getUpdateTime(),
                po.getOperator(),
                po.getStatus()
        );
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<MtCoupon> buildQueryWrapper(Map<String, Object> params) {
        LambdaQueryWrapper<MtCoupon> wrapper = Wrappers.lambdaQuery();

        if (params == null || params.isEmpty()) {
            return wrapper;
        }

        if (params.containsKey("merchantId")) {
            wrapper.eq(MtCoupon::getMerchantId, params.get("merchantId"));
        }
        if (params.containsKey("storeId")) {
            wrapper.eq(MtCoupon::getStoreId, params.get("storeId"));
        }
        if (params.containsKey("type")) {
            wrapper.eq(MtCoupon::getType, params.get("type"));
        }
        if (params.containsKey("status")) {
            wrapper.eq(MtCoupon::getStatus, params.get("status"));
        }

        return wrapper;
    }
}
