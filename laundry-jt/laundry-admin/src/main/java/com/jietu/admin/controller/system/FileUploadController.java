package com.jietu.admin.controller.system;

import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import com.jietu.admin.utils.MyException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传控制器
 *
 * @author: 印修河
 * @date: 2018/12/29 21:41
 */
@RestController
public class FileUploadController extends BaseController {

    /**
     * 上传根路径
     */
    @Value("${upload.root.path}")
    private String uploadRootPath;

    /**
     * 上传文件
     *
     * @param file       图片文件
     * @param uploadPath 上传目录
     * @return
     */
    @PostMapping("/fileUpload")
    public AjaxResult fileUpload(MultipartFile file, String uploadPath) throws Exception {

        Assert.notNull(file, "请上送文件");
        Assert.notNull(uploadPath, "请上送上传文件路径(uploadPath)");
        // 原始名称
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.length() <= 0) {
            throw new MyException("请选择文件上传");
        }
        String fileExtentsion = FilenameUtils.getExtension(originalFilename);
        //写入temp文件
        String filePath = uploadPath + File.separator + DateFormatUtils.format(new Date(), "yyyyMMdd");
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtentsion;
        File tempFile = new File(uploadRootPath + File.separator + filePath + File.separator + fileName);
        if (tempFile.getParentFile() != null || !tempFile.getParentFile().isDirectory()) {
            tempFile.getParentFile().mkdirs();
        }
        file.transferTo(tempFile);

        long size = file.getSize();
        //如果上传文件是图片并且大于500K则压缩到500K
        if (isImage(originalFilename) && size >= (500 * 1024)) {
            double scale = (500 * 1024f) / size;
            //压缩图片
            String thumbnailPathName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtentsion;
            String thumbnailFilePathName = uploadRootPath + File.separator + filePath + File.separator + thumbnailPathName;
            Thumbnails.of(tempFile).scale(1f).outputQuality(scale).outputFormat(fileExtentsion).toFile(thumbnailFilePathName);
            //删除临时文件
            tempFile.delete();
            return new AjaxResult(HttpStatus.OK, "上传文件成功", filePath + "/" + thumbnailPathName);
        }
        return new AjaxResult(HttpStatus.OK, "上传文件成功", filePath + "/" + fileName);
    }

    /**
     * 判断是否为图片
     *
     * @param fileName
     * @return
     */
    private boolean isImage(String fileName) {
        if ("jpg".equalsIgnoreCase(FilenameUtils.getExtension(fileName))
                || "jpeg".equalsIgnoreCase(FilenameUtils.getExtension(fileName))
                || "png".equalsIgnoreCase(FilenameUtils.getExtension(fileName))
                || "gif".equalsIgnoreCase(FilenameUtils.getExtension(fileName))) {
            return true;
        }
        return false;
    }
}
