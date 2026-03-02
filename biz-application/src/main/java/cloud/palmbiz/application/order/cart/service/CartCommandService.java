package cloud.palmbiz.application.order.cart.service;

import cloud.palmbiz.application.order.cart.command.AddToCartCommand;
import cloud.palmbiz.application.order.cart.command.RemoveFromCartCommand;
import cloud.palmbiz.domain.cart.model.CartId;
import cloud.palmbiz.domain.cart.model.CartItem;
import cloud.palmbiz.domain.cart.repository.CartRepository;
import cloud.palmbiz.domain.cart.service.CartValidationService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartCommandService {

    private final CartRepository cartRepository;
    private final CartValidationService validationService;

    @Transactional(rollbackFor = Exception.class)
    public CartItem addToCart(AddToCartCommand command) throws BusinessCheckException {
        if (command == null) {
            throw new BusinessCheckException("添加购物车命令不能为空");
        }

        validationService.validateCartItemAdd(command.getUserId(), command.getGoodsId(), command.getNum());

        // 查找是否已存在相同商品
        CartItem existingItem = cartRepository.findByUserIdAndGoods(
                command.getUserId(), command.getGoodsId(), command.getSkuId());

        if (existingItem != null) {
            // 已存在，更新数量
            String action = command.getAction() != null ? command.getAction() : "+";
            if ("+".equals(action)) {
                existingItem.addQuantity(command.getNum());
            } else if ("-".equals(action)) {
                existingItem.subtractQuantity(command.getNum());
            } else {
                existingItem.setQuantity(command.getNum());
            }
            cartRepository.save(existingItem);
            return existingItem;
        } else {
            // 不存在，新增
            CartItem newItem = CartItem.create(
                    command.getUserId(),
                    command.getMerchantId(),
                    command.getStoreId(),
                    command.getGoodsId(),
                    command.getSkuId(),
                    command.getNum()
            );
            cartRepository.save(newItem);
            return newItem;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeFromCart(RemoveFromCartCommand command) throws BusinessCheckException {
        if (command == null || command.getCartIds() == null) {
            throw new BusinessCheckException("删除购物车命令不能为空");
        }

        String[] ids = command.getCartIds().split(",");
        List<Integer> cartIds = Arrays.stream(ids)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        cartRepository.batchRemove(cartIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Integer userId) throws BusinessCheckException {
        if (userId == null) {
            throw new BusinessCheckException("会员ID不能为空");
        }
        cartRepository.clearByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByHangNo(String hangNo) throws BusinessCheckException {
        if (hangNo == null || hangNo.isEmpty()) {
            throw new BusinessCheckException("挂单号不能为空");
        }
        cartRepository.removeByHangNo(hangNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public CartItem setHangNo(Integer cartId, String hangNo, String isVisitor) throws BusinessCheckException {
        if (cartId == null) {
            throw new BusinessCheckException("购物车ID不能为空");
        }

        validationService.validateHangNo(hangNo);

        CartItem item = cartRepository.findById(CartId.of(cartId));
        if (item == null) {
            throw new BusinessCheckException("购物车项不存在");
        }

        item.setHangNo(hangNo);
        if ("Y".equals(isVisitor)) {
            item.markAsVisitor();
        }

        cartRepository.save(item);
        return item;
    }

    @Transactional(rollbackFor = Exception.class)
    public void switchCartToUser(Integer userId, String cartIds) throws BusinessCheckException {
        if (userId == null || cartIds == null) {
            throw new BusinessCheckException("参数不能为空");
        }

        String[] ids = cartIds.split(",");
        for (String idStr : ids) {
            if (idStr.trim().isEmpty()) continue;
            Integer cartId = Integer.parseInt(idStr.trim());
            CartItem item = cartRepository.findById(CartId.of(cartId));
            if (item != null) {
                item.markAsMember(userId);
                cartRepository.save(item);
            }
        }
    }
}
