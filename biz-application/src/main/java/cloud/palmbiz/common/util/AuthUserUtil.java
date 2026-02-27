package cloud.palmbiz.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import cloud.palmbiz.common.account.dto.AccountInfoDto;

/**
 * 用户认证工具
 */
public class AuthUserUtil {

    private static final ThreadLocal<AccountInfoDto> USER_INFO_IN_TOKEN_HOLDER = new TransmittableThreadLocal<>();

    public static AccountInfoDto get() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }

    public static void set(AccountInfoDto userInfoInTokenBo) {
        USER_INFO_IN_TOKEN_HOLDER.set(userInfoInTokenBo);
    }

    public static void clean() {
      if (USER_INFO_IN_TOKEN_HOLDER.get() != null) {
            USER_INFO_IN_TOKEN_HOLDER.remove();
      }
    }
}
