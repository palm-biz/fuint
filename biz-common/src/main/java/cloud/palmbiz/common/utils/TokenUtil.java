package cloud.palmbiz.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import cloud.palmbiz.common.Constants;
import cloud.palmbiz.common.account.dto.AccountInfoDto;
import cloud.palmbiz.common.user.dto.UserInfoDto;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 登录Token服务接口
 */
@Component
public class TokenUtil {

    public static final int TOKEN_OVER_TIME = 604800;

    public static final String TOKEN_NAME = "Access-Token";

    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 获取后台登录用户信息
     */
    public static AccountInfoDto getAccountInfo() {
        return getAccountInfoByToken(getCurrentRequest().getHeader(TOKEN_NAME));
    }

    /**
     * 获取会员登录信息
     */
    public static UserInfoDto getUserInfo() {
        return getUserInfoByToken(getCurrentRequest().getHeader(TOKEN_NAME));
    }

    /**
     * 生成token
     *
     * @param userAgent
     * @param userId
     * @return
     */
    public static String generateToken(String userAgent, Integer userId) {
        StringBuilder stringBuilder = new StringBuilder();
        UserAgent userAgent1 = UserAgent.parseUserAgentString(userAgent);
        if (userAgent1.getOperatingSystem().isMobileDevice()) {
            stringBuilder.append("APP_");
        } else {
            stringBuilder.append("PC_");
        }

        stringBuilder.append(userId);
        stringBuilder.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_");
        stringBuilder.append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
        String token = MD5Util.getMD5(stringBuilder.toString()).replace("+", "1").replaceAll("&", "8");

        UserInfoDto userLoginInfo = new UserInfoDto();
        userLoginInfo.setId(userId);
        userLoginInfo.setToken(token);
        saveToken(userLoginInfo);

        return token;
    }

    /**
     * 生成token
     *
     * @param userAgent
     * @param accountInfo
     * @return
     */
    public static String generateToken(String userAgent, AccountInfoDto accountInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        UserAgent userAgent1 = UserAgent.parseUserAgentString(userAgent);
        if (userAgent1.getOperatingSystem().isMobileDevice()) {
            stringBuilder.append("APP_");
        } else {
            stringBuilder.append("PC_");
        }

        stringBuilder.append(accountInfo.getId());
        stringBuilder.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_");
        stringBuilder.append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
        String token = MD5Util.getMD5(stringBuilder.toString()).replace("+", "1").replaceAll("&", "8");

        accountInfo.setToken(token);
        saveAccountToken(accountInfo);

        return token;
    }

    /**
     * 保存token
     *
     * @param userInfo
     * @return
     */
    public static void saveToken(UserInfoDto userInfo) {
        if (userInfo == null || userInfo.getToken() == null || userInfo.getId() == null) {
            return;
        }
        RedisUtil.set(Constants.SESSION_USER + userInfo.getToken(), userInfo, TOKEN_OVER_TIME);
    }

    /**
     * 通过token获取后台登录信息
     *
     * @param token
     * @return
     */
    public static UserInfoDto getUserInfoByToken(String token) {
        if (token == null || StringUtil.isEmpty(token)) {
            return null;
        }
        Object loginInfo = RedisUtil.get(Constants.SESSION_USER + token);
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDto userInfo = objectMapper.convertValue(loginInfo, UserInfoDto.class);
        if (userInfo != null && userInfo.getToken().equals(token)) {
            return userInfo;
        }
        return null;
    }

    /**
     * 检查是否登录
     *
     * @param token
     * @return
     */
    public static boolean checkTokenLogin(String token) {
        try {
            UserInfoDto userInfo = RedisUtil.get(Constants.SESSION_USER + token);
            if (userInfo != null && userInfo.getToken().equals(token)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 删除登录信息
     *
     * @param token
     * @return
     */
    public static boolean removeToken(String token) {
        RedisUtil.remove(token);
        AuthUserUtil.clean();
        return true;
    }

    /**
     * 保存后台登录token
     *
     * @param accountInfo
     * @return
     */
    public static void saveAccountToken(AccountInfoDto accountInfo) {
        if (accountInfo == null) {
            return;
        }
        RedisUtil.set(Constants.SESSION_ADMIN_USER + accountInfo.getToken(), accountInfo, TOKEN_OVER_TIME);
    }

    /**
     * 通过登录token获取后台登录信息
     *
     * @param token
     * @return
     */
    public static AccountInfoDto getAccountInfoByToken(String token) {
        Object loginInfo = RedisUtil.get(Constants.SESSION_ADMIN_USER + token);
        ObjectMapper objectMapper = new ObjectMapper();
        AccountInfoDto accountInfo = objectMapper.convertValue(loginInfo, AccountInfoDto.class);
        if (accountInfo != null && accountInfo.getToken().equals(token)) {
            return accountInfo;
        }
        return null;
    }
}
