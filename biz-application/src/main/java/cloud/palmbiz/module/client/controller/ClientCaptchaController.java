package cloud.palmbiz.module.client.controller;

import cloud.palmbiz.common.service.CaptchaService;
import cloud.palmbiz.common.util.Base64Util;
import cloud.palmbiz.framework.web.BaseController;
import cloud.palmbiz.framework.web.ResponseObject;
import cloud.palmbiz.common.utils.SeqUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图形验证码控制类
 */
@Api(tags="会员端-图形验证码相关接口")
@RestController
@AllArgsConstructor
@RequestMapping("/client/captcha")
public class ClientCaptchaController extends BaseController {

    /**
     * 图形验证码服务接口
     */
    private CaptchaService captchaService;

    @ApiOperation(value = "获取图形验证码")
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseObject getCode(HttpServletResponse response) {
        String captcha = "";
        String uuid = SeqUtil.getUUID();

        try {
            BufferedImage image = captchaService.getCodeByUuid(uuid);
            // 输出流
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);
            captcha = new String(Base64Util.baseEncode(stream.toByteArray()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-cache");

        Map<String, Object> outParams = new HashMap<String, Object>();
        outParams.put("code", "data:image/jpg;base64," + captcha);
        outParams.put("uuid", uuid);

        return getSuccessResult(outParams);
    }

    @ApiOperation(value = "校验图形验证码")
    @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseObject checkCode(@RequestParam String code, HttpServletRequest request) {
        String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");

        Boolean result = captchaService.checkCodeByUuid(code, uuid);
        Map<String, Object> outParams = new HashMap<String, Object>();
        outParams.put("result", result);

        return getSuccessResult(outParams);
    }
}
