//package com.jietu.common.service;
//
//import com.aliyun.oss.ClientConfiguration;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.common.auth.DefaultCredentialProvider;
//import com.aliyun.oss.model.OSSObject;
//import com.aliyun.oss.model.ObjectMetadata;
//import com.aliyun.oss.model.PutObjectResult;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//
///**
// * 阿里云oss存储服务
// * @author: 印修河
// * @date: 2018/12/22 13:36
// */
//@Service
//public class AliyunOssService {
//
//    private static final Logger logger = LoggerFactory.getLogger(AliyunOssService.class);
//    /** 阿里云API的密钥Access Key ID */
//    @Value("${aliyun.accessKeyId}")
//    private String accessKeyId;
//    /** 阿里云API的密钥Access Key Secret */
//    @Value("${aliyun.accessKeySecret}")
//    private String accessKeySecret;
//    /** 阿里云API的内或外网域名 */
//    @Value("${aliyun.endpoint}")
//    private String endpoint;
//    /** 阿里云API的bucket名称 */
//    @Value("${aliyun.bucketName}")
//    private String bucketName;
//
//    private OSSClient ossClient;
//
//    /**
//     * 初始化ossClient
//     */
//    @PostConstruct
//    public void init(){
//        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
//        ClientConfiguration conf = new ClientConfiguration();
//        ossClient = new OSSClient(endpoint, credentialProvider, conf);
//    }
//
//    /**
//     * 创建文件夹
//     *
//     * @param folder 文件夹名如"qj_nanjing/"
//     * @return 文件夹名
//     */
//    public String createFolder(String folder) {
//        // 文件夹名
//        final String keySuffixWithSlash = folder;
//        // 判断文件夹是否存在，不存在则创建
//        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
//            // 创建文件夹
//            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
//            logger.info("创建oss文件夹成功");
//            // 得到文件夹名
//            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
//            String fileDir = object.getKey();
//            return fileDir;
//        }
//        return keySuffixWithSlash;
//    }
//
//    /**
//     * 根据key删除OSS服务器上的文件
//     *
//     * @param folder 模拟文件夹名 如"qj_nanjing/"
//     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
//     */
//    public void deleteFile(String folder, String key) {
//        ossClient.deleteObject(bucketName, folder + key);
//        logger.info("删除{}下的{}[{}文件成功", bucketName, folder, key);
//    }
//
//    /**
//     * 上传文件至OSS
//     *
//     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
//     * @param folder 模拟文件夹名 如"qj_nanjing/"
//     * @return String 返回的唯一MD5数字签名
//     */
//    public String uploadFile2OSS(File file, String folder, String fileName) {
//        logger.info("开始上传文件{}/{}到阿里云OSS", folder, fileName);
//        String resultStr = null;
//        try {
//            // 以输入流的形式上传文件
//            InputStream is = new FileInputStream(file);
//            // 文件大小
//            Long fileSize = file.length();
//            // 创建上传Object的Metadata
//            ObjectMetadata metadata = new ObjectMetadata();
//            // 上传的文件的长度
//            metadata.setContentLength(is.available());
//            // 指定该Object被下载时的网页的缓存行为
//            metadata.setCacheControl("no-cache");
//            // 指定该Object下设置Header
//            metadata.setHeader("Pragma", "no-cache");
//            // 指定该Object被下载时的内容编码格式
//            metadata.setContentEncoding("utf-8");
//            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
//            // 如果没有扩展名则填默认值application/octet-stream
//            metadata.setContentType(getContentType(fileName));
//            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
//            // 上传文件 (上传文件流的形式)
//            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
//            // 解析结果
//            resultStr = putResult.getETag();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("上传阿里云OSS服务器异常:" + e.getMessage(), e);
//        }
//        logger.info("结束上传文件{}/{}到阿里云OSS", folder, fileName);
//        return resultStr;
//    }
//
//    /**
//     * 上传文件至OSS
//     *
//     * @param is 文件输入流
//     * @param folder 模拟文件夹名 如"qj_nanjing/"
//     * @param fileName 文件名称
//     * @param fileSize 文件大小
//     * @return String 返回的唯一MD5数字签名
//     */
//    public String uploadFile2OSS(InputStream is, String folder, String fileName, Long fileSize) {
//        logger.info("开始上传文件{}/{}到阿里云OSS", folder, fileName);
//        //由于阿里云OSS不允许/打头，所以在此判断如果是/打头则去掉
//        if(StringUtils.isNotBlank(folder) && "/".equals(folder.substring(0,1))){
//            folder = folder.substring(1);
//        }
//
//        String resultStr = null;
//        try {
//            // 创建上传Object的Metadata
//            ObjectMetadata metadata = new ObjectMetadata();
//            // 上传的文件的长度
//            metadata.setContentLength(is.available());
//            // 指定该Object被下载时的网页的缓存行为
//            metadata.setCacheControl("no-cache");
//            // 指定该Object下设置Header
//            metadata.setHeader("Pragma", "no-cache");
//            // 指定该Object被下载时的内容编码格式
//            metadata.setContentEncoding("utf-8");
//            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
//            // 如果没有扩展名则填默认值application/octet-stream
//            metadata.setContentType(getContentType(fileName));
//            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
//            // 上传文件 (上传文件流的形式)
//            PutObjectResult putResult = ossClient.putObject(bucketName, folder + "/" + fileName, is, metadata);
//            // 解析结果
//            resultStr = putResult.getETag();
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("上传阿里云OSS服务器异常:" + e.getMessage(), e);
//        }
//        logger.info("结束上传文件{}/{}到阿里云OSS", folder, fileName);
//        return resultStr;
//    }
//
//    /**
//     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
//     *
//     * @param fileName 文件名
//     * @return 文件的contentType
//     */
//    public String getContentType(String fileName) {
//        // 文件的后缀名
//        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
//        if (".bmp".equalsIgnoreCase(fileExtension)) {
//            return "image/bmp";
//        }
//        if (".gif".equalsIgnoreCase(fileExtension)) {
//            return "image/gif";
//        }
//        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
//                || ".png".equalsIgnoreCase(fileExtension)) {
//            return "image/jpeg";
//        }
//        if (".png".equalsIgnoreCase(fileExtension)) {
//            return "image/png";
//        }
//        if (".html".equalsIgnoreCase(fileExtension)) {
//            return "text/html";
//        }
//        if (".txt".equalsIgnoreCase(fileExtension)) {
//            return "text/plain";
//        }
//        if (".vsd".equalsIgnoreCase(fileExtension)) {
//            return "application/vnd.visio";
//        }
//        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
//            return "application/vnd.ms-powerpoint";
//        }
//        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
//            return "application/msword";
//        }
//        if (".xml".equalsIgnoreCase(fileExtension)) {
//            return "text/xml";
//        }
//        // 默认返回类型
//        return "";
//    }
//
//}
