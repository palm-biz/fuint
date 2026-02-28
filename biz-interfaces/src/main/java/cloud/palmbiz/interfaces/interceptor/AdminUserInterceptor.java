package cloud.palmbiz.interfaces.interceptor;

import cloud.palmbiz.common.Constants;
import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.util.AuthUserUtil;
import cloud.palmbiz.common.util.TokenUtil;
import cloud.palmbiz.common.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台登录拦截
 */
public class AdminUserInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Access-Token");

        // 验证Token
        if (StringUtils.isEmpty(accessToken)) {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getOutputStream().print("{\"code\":1001,\"message\":\"" + PropertiesUtil
                    .getResponseErrorMessageByCode(Constants.HTTP_RESPONSE_CODE_NOLOGIN) + "\",\"data\":null}");
            return false;
        }

        AccountInfoDto accountInfo = TokenUtil.getAccountInfoByToken(accessToken);
        // 验证session中的Token
        if (accountInfo != null && accountInfo.getToken().equals(accessToken)) {
            AuthUserUtil.set(accountInfo);
            return true;
        }

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getOutputStream().print("{\"code\":1001,\"message\":\"" + PropertiesUtil
                .getResponseErrorMessageByCode(Constants.HTTP_RESPONSE_CODE_NOLOGIN) + "\",\"data\":null}");
        return false;
    }
}
