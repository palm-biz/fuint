package cloud.palmbiz.application.cart.service;

import cloud.palmbiz.application.cart.query.CartQuery;
import cloud.palmbiz.domain.cart.model.CartItem;
import cloud.palmbiz.domain.cart.repository.CartRepository;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartQueryService {

    private final CartRepository cartRepository;

    public List<CartItem> queryByUserId(Integer userId) throws BusinessCheckException {
        if (userId == null) {
            throw new BusinessCheckException("会员ID不能为空");
        }
        return cartRepository.findByUserId(userId);
    }

    public List<CartItem> queryByHangNo(String hangNo) throws BusinessCheckException {
        if (hangNo == null || hangNo.isEmpty()) {
            throw new BusinessCheckException("挂单号不能为空");
        }
        return cartRepository.findByHangNo(hangNo);
    }

    public List<CartItem> queryByParams(CartQuery query) {
        if (query == null) {
            query = new CartQuery();
        }
        return cartRepository.findByParams(query.toParams());
    }

    public Long countByUserId(Integer userId) {
        if (userId == null) {
            return 0L;
        }
        return cartRepository.countByUserId(userId);
    }
}
