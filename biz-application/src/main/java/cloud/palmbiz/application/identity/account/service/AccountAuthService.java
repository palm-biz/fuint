package cloud.palmbiz.application.identity.account.service;

import cloud.palmbiz.application.identity.account.command.LoginCommand;
import cloud.palmbiz.application.identity.account.dto.AccountInfoDto;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.application.CaptchaService;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.common.utils.StringUtil;
import cloud.palmbiz.domain.account.model.Account;
import cloud.palmbiz.domain.account.repository.AccountRepository;
import cloud.palmbiz.framework.annoation.OperationServiceLog;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.mapper.MtMerchantMapper;
import cloud.palmbiz.infrastructure.mapper.MtStoreMapper;
import cloud.palmbiz.infrastructure.model.MtMerchant;
import cloud.palmbiz.infrastructure.model.MtStore;
import cloud.palmbiz.module.backendApi.response.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 账号认证服务
 * 处理登录认证相关操作
 *
 * @author DDD Refactoring
 */
@Service
@AllArgsConstructor
public class AccountAuthService {

    private final AccountRepository accountRepository;
    private final AccountQueryService accountQueryService;
    private final CaptchaService captchaService;
    private final MtMerchantMapper mtMerchantMapper;
    private final MtStoreMapper mtStoreMapper;

    /**
     * 登录后台系统
     */
    @OperationServiceLog(description = "登录后台系统")
    public LoginResponse login(LoginCommand command) throws BusinessCheckException {
        // 验证参数
        if (StringUtil.isEmpty(command.getUsername())
                || StringUtil.isEmpty(command.getPassword())
                || StringUtil.isEmpty(command.getCaptchaCode())) {
            throw new BusinessCheckException("登录参数有误");
        }

        // 验证图形验证码
        Boolean captchaVerify = captchaService.checkCodeByUuid(command.getCaptchaCode(), command.getUuid());
        if (!captchaVerify) {
            throw new BusinessCheckException("图形验证码有误");
        }

        // 查询账号
        Account account = accountRepository.findByName(command.getUsername());
        if (account == null) {
            throw new BusinessCheckException("登录账号或密码有误");
        }

        // 验证密码
        if (!account.verifyPassword(command.getPassword())) {
            throw new BusinessCheckException("登录账号或密码有误");
        }

        // 验证账号状态
        if (!account.isActive()) {
            throw new BusinessCheckException("登录账号或密码有误");
        }

        // 验证商户状态
        if (account.getMerchantId() != null && account.getMerchantId() > 0) {
            MtMerchant mtMerchant = mtMerchantMapper.selectById(account.getMerchantId());
            if (mtMerchant != null && !mtMerchant.getStatus().equals(StatusEnum.ENABLED.getKey())) {
                throw new BusinessCheckException("您的商户已被禁用，请联系平台方");
            }
        }

        // 验证店铺状态
        if (account.getStoreId() != null && account.getStoreId() > 0) {
            MtStore mtStore = mtStoreMapper.selectById(account.getStoreId());
            if (mtStore != null && !mtStore.getStatus().equals(StatusEnum.ENABLED.getKey())) {
                throw new BusinessCheckException("您的店铺已被禁用，请联系平台方");
            }
        }

        // 生成Token
        AccountInfoDto accountInfo = accountQueryService.queryById(account.getAccountIdValue());
        String token = TokenUtil.generateToken(command.getUserAgent(), accountInfo);

        // 构造响应
        LoginResponse response = new LoginResponse();
        response.setLogin(true);
        response.setToken(token);
        response.setTokenCreatedTime(new Date());

        return response;
    }
}
