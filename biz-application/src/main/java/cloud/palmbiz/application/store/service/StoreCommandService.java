package cloud.palmbiz.application.store.service;

import cloud.palmbiz.application.store.command.CreateStoreCommand;
import cloud.palmbiz.application.store.command.UpdateStoreCommand;
import cloud.palmbiz.common.enums.YesOrNoEnum;
import cloud.palmbiz.domain.store.model.Store;
import cloud.palmbiz.domain.store.model.StoreId;
import cloud.palmbiz.domain.store.repository.StoreRepository;
import cloud.palmbiz.domain.store.service.StoreValidationService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店铺命令服务
 */
@Service
@RequiredArgsConstructor
public class StoreCommandService {

    private final StoreRepository storeRepository;
    private final StoreValidationService validationService;

    /**
     * 创建店铺
     */
    @Transactional(rollbackFor = Exception.class)
    public Store createStore(CreateStoreCommand command) throws BusinessCheckException {
        validationService.validateMerchantId(command.getMerchantId());
        validationService.validateStoreName(command.getName());
        validationService.validatePhone(command.getPhone());

        // 创建店铺
        Store store = Store.create(
            command.getMerchantId(),
            command.getName(),
            command.getContact(),
            command.getPhone(),
            command.getAddress(),
            command.getOperator()
        );

        // 设置其他信息
        if (command.getLogo() != null) {
            store.updateBasicInfo(command.getName(), command.getLogo(), command.getContact(),
                    command.getPhone(), command.getAddress(), command.getHours(),
                    command.getDescription(), command.getOperator());
        }

        // 设置位置
        if (command.getLatitude() != null && command.getLongitude() != null) {
            validationService.validateCoordinate(command.getLatitude(), command.getLongitude());
            store.setLocation(command.getLatitude(), command.getLongitude());
        }

        // 设置营业执照信息
        if (command.getLicense() != null || command.getCreditCode() != null) {
            store.updateLicenseInfo(command.getLicense(), command.getCreditCode());
        }

        // 设置银行账户
        if (command.getBankName() != null) {
            store.updateBankAccount(command.getBankName(), command.getBankCardName(), command.getBankCardNo());
        }

        // 设置支付配置
        if (command.getWxMchId() != null) {
            store.updateWechatPayment(command.getWxMchId(), command.getWxApiV2(), command.getWxCertPath());
        }
        if (command.getAlipayAppId() != null) {
            store.updateAlipayPayment(command.getAlipayAppId(), command.getAlipayPrivateKey(), command.getAlipayPublicKey());
        }

        // 处理默认店铺
        if (YesOrNoEnum.YES.getKey().equals(command.getIsDefault())) {
            storeRepository.resetDefaultStore(command.getMerchantId());
            store.setAsDefault();
        }

        storeRepository.save(store);
        return store;
    }

    /**
     * 更新店铺
     */
    @Transactional(rollbackFor = Exception.class)
    public Store updateStore(UpdateStoreCommand command) throws BusinessCheckException {
        Store store = storeRepository.findById(StoreId.of(command.getId()));
        if (store == null) {
            throw new BusinessCheckException("该店铺不存在");
        }

        validationService.validateStoreName(command.getName());
        validationService.validatePhone(command.getPhone());

        // 更新基本信息
        store.updateBasicInfo(
            command.getName(),
            command.getLogo(),
            command.getContact(),
            command.getPhone(),
            command.getAddress(),
            command.getHours(),
            command.getDescription(),
            command.getOperator()
        );

        // 更新位置
        if (command.getLatitude() != null && command.getLongitude() != null) {
            validationService.validateCoordinate(command.getLatitude(), command.getLongitude());
            store.setLocation(command.getLatitude(), command.getLongitude());
        }

        // 更新营业执照信息
        if (command.getLicense() != null || command.getCreditCode() != null) {
            store.updateLicenseInfo(command.getLicense(), command.getCreditCode());
        }

        // 更新银行账户
        if (command.getBankName() != null) {
            store.updateBankAccount(command.getBankName(), command.getBankCardName(), command.getBankCardNo());
        }

        // 更新支付配置
        if (command.getWxMchId() != null) {
            store.updateWechatPayment(command.getWxMchId(), command.getWxApiV2(), command.getWxCertPath());
        }
        if (command.getAlipayAppId() != null) {
            store.updateAlipayPayment(command.getAlipayAppId(), command.getAlipayPrivateKey(), command.getAlipayPublicKey());
        }

        // 处理默认店铺
        if (YesOrNoEnum.YES.getKey().equals(command.getIsDefault())) {
            storeRepository.resetDefaultStore(store.getMerchantId());
            store.setAsDefault();
        } else if (YesOrNoEnum.NO.getKey().equals(command.getIsDefault())) {
            store.unsetDefault();
        }

        storeRepository.save(store);
        return store;
    }

    /**
     * 更新店铺状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String operator, String status) throws BusinessCheckException {
        Store store = storeRepository.findById(StoreId.of(id));
        if (store == null) {
            throw new BusinessCheckException("该店铺不存在");
        }

        if ("A".equals(status)) {
            store.enable(operator);
        } else if ("D".equals(status)) {
            store.disable(operator);
            // 删除店铺商品关联
            storeRepository.removeStoreGoods(id);
        } else if ("N".equals(status)) {
            store.delete(operator);
        }

        storeRepository.save(store);
    }

    /**
     * 设置二维码
     */
    @Transactional(rollbackFor = Exception.class)
    public void setQrCode(Integer id, String qrCode) throws BusinessCheckException {
        Store store = storeRepository.findById(StoreId.of(id));
        if (store == null) {
            throw new BusinessCheckException("该店铺不存在");
        }

        store.setQrCode(qrCode);
        storeRepository.save(store);
    }

    /**
     * 根据商户ID删除店铺
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMerchantId(Integer merchantId) {
        if (merchantId != null && merchantId > 0) {
            storeRepository.removeByMerchantId(merchantId);
        }
    }
}
