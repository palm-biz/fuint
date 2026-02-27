package cloud.palmbiz.common.service;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传服务类
 */
public interface UploadService {

    /**
     * 保存文件
     *
     * @param request
     * @param file excel文件
     * @return
     */
    String saveUploadFile(HttpServletRequest request, MultipartFile file) throws Exception;

}
