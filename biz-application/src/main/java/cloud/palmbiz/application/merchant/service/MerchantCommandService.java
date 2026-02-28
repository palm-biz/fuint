package cloud.palmbiz.application.merchant.service;

import cloud.palmbiz.application.merchant.command.CreateMerchantCommand;
import cloud.palmbiz.application.merchant.command.UpdateMerchantCommand;
import cloud.palmbiz.domain.merchant.model.Merchant;
import cloud.palmbiz.domain.merchant.model.MerchantId;
import cloud.palmbiz.domain.merchant.repository.MerchantRepository;
import cloud.palmbiz.domain.merchant.service.MerchantValidationService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商户命令服务
 */
@Service
@RequiredArgsConstructor
public class MerchantCommandService {

    private final MerchantRepository merchantRepository;
    private final MerchantValidationService validationService;

    /**
     * 创建商户
     */
    @Transactional(rollbackFor = Exception.class)
    public Merchant createMerchant(CreateMerchantCommand command) throws BusinessCheckException {
        validationService.validateMerchantName(command.getName());
        validationService.validateMerchantNo(command.getMerchantNo());
        validationService.validatePhone(command.getPhone());
        validationService.validateSettleRate(command.getSettleRate());

        // 检查商户名称是否已存在
        Merchant existingByName = merchantRepository.findByName(command.getName());
        if (existingByName != null) {
            throw new BusinessCheckException("该商户名称已经存在");
        }

        // 检查商户号是否已存在
        Merchant existingByNo = merchantRepository.findByNo(command.getMerchantNo());
        if (existingByNo != null) {
            throw new BusinessCheckException("该商户号已经存在");
        }

        // 创建商户
        Merchant merchant = Merchant.create(
            command.getMerchantNo(),
            command.getName(),
            command.getType(),
            command.getContact(),
            command.getPhone(),
            command.getOperator()
        );

        // 设置其他信息
        if (command.getLogo() != null) {
            merchant.updateBasicInfo(command.getName(), command.getLogo(), command.getContact(),
                    command.getPhone(), command.getAddress(), command.getDescription(),
                    command.getOperator());
        }

        // 设置微信配置
        if (command.getWxAppId() != null) {
            merchant.updateWechatMiniProgram(command.getWxAppId(), command.getWxAppSecret());
        }
        if (command.getWxOfficialAppId() != null) {
            merchant.updateWechatOfficialAccount(command.getWxOfficialAppId(), command.getWxOfficialAppSecret());
        }

        // 设置结算比例
        if (command.getSettleRate() != null) {
            merchant.updateSettleRate(command.getSettleRate());
        }

        merchantRepository.save(merchant);
        return merchant;
    }

    /**
     * 更新商户
     */
    @Transactional(rollbackFor = Exception.class)
    public Merchant updateMerchant(UpdateMerchantCommand command) throws BusinessCheckException {
        Merchant merchant = merchantRepository.findById(MerchantId.of(command.getId()));
        if (merchant == null) {
            throw new BusinessCheckException("该商户不存在");
        }

        validationService.validateMerchantName(command.getName());
        validationService.validatePhone(command.getPhone());
        validationService.validateSettleRate(command.getSettleRate());

        // 检查商户名称是否重复
        Merchant existingByName = merchantRepository.findByName(command.getName());
        if (existingByName != null && !existingByName.getId().equals(command.getId())) {
            throw new BusinessCheckException("该商户名称已经存在");
        }

        // 检查商户号是否重复
        if (command.getMerchantNo() != null && !command.getMerchantNo().equals(merchant.getNo())) {
            validationService.validateMerchantNo(command.getMerchantNo());
            Merchant existingByNo = merchantRepository.findByNo(command.getMerchantNo());
            if (existingByNo != null && !existingByNo.getId().equals(command.getId())) {
                throw new BusinessCheckException("该商户号已经存在");
            }
            merchant.updateMerchantNo(command.getMerchantNo());
        }

        // 更新基本信息
        merchant.updateBasicInfo(
            command.getName(),
            command.getLogo(),
            command.getContact(),
            command.getPhone(),
            command.getAddress(),
            command.getDescription(),
            command.getOperator()
        );

        // 更新类型
        if (command.getType() != null) {
            merchant.updateType(command.getType());
        }

        // 更新微信配置
        if (command.getWxAppId() != null) {
            merchant.updateWechatMiniProgram(command.getWxAppId(), command.getWxAppSecret());
        }
        if (command.getWxOfficialAppId() != null) {
            merchant.updateWechatOfficialAccount(command.getWxOfficialAppId(), command.getWxOfficialAppSecret());
        }

        // 更新结算比例
        if (command.getSettleRate() != null) {
            merchant.updateSettleRate(command.getSettleRate());
        }

        merchantRepository.save(merchant);
        return merchant;
    }

    /**
     * 更新商户状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String operator, String status) throws BusinessCheckException {
        Merchant merchant = merchantRepository.findById(MerchantId.of(id));
        if (merchant == null) {
            throw new BusinessCheckException("该商户不存在");
        }

        if ("A".equals(status)) {
            merchant.enable(operator);
        } else if ("D".equals(status)) {
            merchant.disable(operator);
        } else if ("N".equals(status)) {
            merchant.delete(operator);
        }

        merchantRepository.save(merchant);
    }

    /**
     * 删除商户
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteMerchant(Integer id, String operator) throws BusinessCheckException {
        Merchant merchant = merchantRepository.findById(MerchantId.of(id));
        if (merchant == null) {
            throw new BusinessCheckException("该商户不存在");
        }

        merchant.delete(operator);
        merchantRepository.save(merchant);
    }
}
