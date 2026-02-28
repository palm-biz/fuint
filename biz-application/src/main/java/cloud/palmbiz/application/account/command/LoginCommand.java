package cloud.palmbiz.application.account.command;

import lombok.Data;

/**
 * 登录命令
 *
 * @author DDD Refactoring
 */
@Data
public class LoginCommand {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captchaCode;

    /**
     * 验证码UUID
     */
    private String uuid;

    /**
     * 用户代理（浏览器信息）
     */
    private String userAgent;
}
