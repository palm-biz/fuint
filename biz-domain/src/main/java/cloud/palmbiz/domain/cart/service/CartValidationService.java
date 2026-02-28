package cloud.palmbiz.domain.cart.service;

import cloud.palmbiz.domain.cart.model.CartItem;
import org.springframework.stereotype.Service;

/**
 * 购物车验证领域服务
 * 负责购物车相关的业务规则验证
 *
 * @author DDD Refactoring
 */
@Service
public class CartValidationService {

    /**
     * 验证购物车项是否可以添加
     */
    public void validateCartItemAdd(Integer userId, Integer goodsId, Double num) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("会员ID不能为空");
        }
        if (goodsId == null || goodsId <= 0) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (num == null || num <= 0) {
            throw new IllegalArgumentException("商品数量必须大于0");
        }
    }

    /**
     * 验证数量是否合法
     */
    public void validateQuantity(Double num) {
        if (num == null || num <= 0) {
            throw new IllegalArgumentException("数量必须大于0");
        }
        if (num > 9999) {
            throw new IllegalArgumentException("数量不能超过9999");
        }
    }

    /**
     * 验证购物车项是否可以操作
     */
    public void validateCartItemOperation(CartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("购物车项不存在");
        }
        if (item.getStatus().isDeleted()) {
            throw new IllegalStateException("购物车项已删除");
        }
    }

    /**
     * 验证挂单号
     */
    public void validateHangNo(String hangNo) {
        if (hangNo == null || hangNo.trim().isEmpty()) {
            throw new IllegalArgumentException("挂单号不能为空");
        }
    }

    /**
     * 验证购物车所属
     */
    public void validateCartOwnership(CartItem item, Integer userId) {
        if (item == null) {
            throw new IllegalArgumentException("购物车项不存在");
        }
        if (userId == null) {
            throw new IllegalArgumentException("会员ID不能为空");
        }
        if (!userId.equals(item.getUserId())) {
            throw new IllegalStateException("购物车项不属于当前会员");
        }
    }
}
