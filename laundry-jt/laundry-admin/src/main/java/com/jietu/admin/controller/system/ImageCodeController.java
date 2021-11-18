package com.jietu.admin.controller.system;

import com.jietu.common.utils.ImageCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码控制器
 * @author: 印修河
 * @date: 2018/11/11 22:32
 */
@Controller
public class ImageCodeController extends BaseController {

    @RequestMapping("imageCode")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(ImageCode.getImage(request), "jpeg", sos);
        sos.close();
    }

}
