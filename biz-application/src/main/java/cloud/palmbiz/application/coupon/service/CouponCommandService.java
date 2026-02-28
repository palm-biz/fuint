package cloud.palmbiz.application.coupon.service;

import cloud.palmbiz.application.coupon.command.CreateCouponCommand;
import cloud.palmbiz.application.coupon.command.UpdateCouponStatusCommand;
import cloud.palmbiz.domain.coupon.model.Coupon;
import cloud.palmbiz.domain.coupon.model.CouponId;
import cloud.palmbiz.domain.coupon.model.CouponType;
import cloud.palmbiz.domain.coupon.repository.CouponRepository;
import cloud.palmbiz.domain.coupon.service.CouponValidationService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;
    private final CouponValidationService validationService;

    @Transactional(rollbackFor = Exception.class)
    public Coupon createCoupon(CreateCouponCommand command) throws BusinessCheckException {
        if (command == null) {
            throw new BusinessCheckException("创建卡券命令不能为空");
        }

        validationService.validateCouponCreation(command.getName(), command.getTotal());

        Coupon coupon = Coupon.create(
                command.getName(),
                CouponType.fromCode(command.getType()),
                command.getMerchantId(),
                command.getAmount(),
                command.getTotal()
        );

        if (command.getExpireType() != null && "FIX".equals(command.getExpireType())) {
            validationService.validateValidPeriod(command.getBeginTime(), command.getEndTime());
            coupon.setValidPeriod(command.getBeginTime(), command.getEndTime());
        } else if (command.getExpireTime() != null) {
            validationService.validateValidDays(command.getExpireTime());
            coupon.setValidDays(command.getExpireTime());
        }

        if (command.getDescription() != null) {
            coupon.setInfo(command.getName(), command.getDescription(), command.getImage());
        }
        if (command.getLimitNum() != null) {
            coupon.setLimitNum(command.getLimitNum());
        }
        if (command.getIsGive() != null) {
            coupon.setIsGive(command.getIsGive());
        }
        if (command.getReceiveCode() != null) {
            coupon.setReceiveCode(command.getReceiveCode());
        }

        couponRepository.save(coupon);
        return coupon;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateCouponStatusCommand command) throws BusinessCheckException {
        if (command == null || command.getCouponId() == null) {
            throw new BusinessCheckException("更新状态命令不能为空");
        }

        Coupon coupon = couponRepository.findById(CouponId.of(command.getCouponId()));
        if (coupon == null) {
            throw new BusinessCheckException("卡券不存在");
        }

        validationService.validateStatusChange(coupon);

        if ("A".equals(command.getStatus())) {
            coupon.activate(command.getOperator());
        } else if ("D".equals(command.getStatus())) {
            coupon.delete(command.getOperator());
        } else if ("N".equals(command.getStatus())) {
            coupon.disable(command.getOperator());
        }

        couponRepository.save(coupon);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCoupon(Integer couponId, String operator) throws BusinessCheckException {
        if (couponId == null) {
            throw new BusinessCheckException("卡券ID不能为空");
        }

        Coupon coupon = couponRepository.findById(CouponId.of(couponId));
        if (coupon == null) {
            throw new BusinessCheckException("卡券不存在");
        }

        coupon.delete(operator);
        couponRepository.save(coupon);
    }
}
