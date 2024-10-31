package com.fang.screw.communal.template.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import com.fang.screw.communal.config.MinioProperties;
import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.template.OssTemplate;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Stream;

/**
 * @FileName MinIOTemplate
 * @Description
 * @Author yaoHui
 * @date 2024-07-30
 **/
@Slf4j
@Service
public class MinIOTemplate implements OssTemplate {


    /**
     * MinIO客户端
     */
    @Resource
    private MinioClient client;

    /**
     * 配置类
     */
    @Resource
    private MinioProperties ossProperties;

    /**
     * 格式化时间
     */
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 字符集
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    @Override
    public boolean bucketExists(String bucketName) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucketName)).build());
        } catch (Exception e) {
            log.error("minio bucketExists Exception:{}", e);
        }
        return false;
    }

    /**
     * @Description: 创建 存储桶
     * @Param bucketName: 存储桶名称
     * @return: void
     * @Date: 2023/8/2 11:28
     */
    public void makeBucket(String bucketName) {
        try {
            if (!client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucketName)).build())) {
                client.makeBucket(MakeBucketArgs.builder().bucket(getBucketName(bucketName)).build());
                log.info("minio makeBucket success bucketName:{}", bucketName);
            }
        } catch (Exception e) {
            log.error("minio makeBucket Exception:{}", e);
        }
    }

    /**
     * 获取文件信息
     *
     * @param fileName 存储桶文件名称
     * @return InputStream
     */
    @Override
    public OssFile getOssInfo(String fileName) {
        try {
            StatObjectResponse stat = client.statObject(
                    StatObjectArgs.builder().bucket(getBucketName(ossProperties.getBucketName())).object(fileName)
                            .build());
            OssFile ossFile = new OssFile();
            ossFile.setName(ObjectUtil.isEmpty(stat.object()) ? fileName : stat.object());
            ossFile.setFilePath(ossFile.getName());
            ossFile.setDomain(getOssHost(ossProperties.getBucketName()));
            ossFile.setHash(String.valueOf(stat.hashCode()));
            ossFile.setSize((int) stat.size());
            ossFile.setPutTime(DateUtil.date(stat.lastModified().toLocalDateTime()));
            ossFile.setContentType(stat.contentType());
            return ossFile;
        } catch (Exception e) {
            log.error("minio getOssInfo Exception:{}", e);
        }
        return null;
    }

    /**
     * 获取文件
     *
     * @param fileName 文件地址
     * @return InputStream
     */
    @Override
    public InputStream getOssFile(String fileName) {
        try {
            return client.getObject(GetObjectArgs.builder().bucket(getBucketName(ossProperties.getBucketName()))
                    .object(fileName).build());
        } catch (Exception e) {
            log.error("minio getOssInfo Exception:{}", e);
        }
        return null;
    }


    /**
     * 上传文件
     *
     * @param folderName 上传的文件夹名称
     * @param fileName   上传文件名
     * @param file       上传文件类
     * @return BladeFile
     */
    @Override
    @SneakyThrows
    public OssFile upLoadFile(String folderName, String fileName, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }
//        // 文件大小
//        if (file.getSize() > 5 * 1024 * 1024) {
//            throw new RuntimeException("文件大小不能超过5M");
//        }
        String suffix = getFileExtension(file.getOriginalFilename());
        // 文件后缀判断
        if (!CollUtil.contains(ossProperties.getFileExt(), suffix)) {
            String error = String.format("文件类型错误,目前支持[%s]等文件类型",
                    String.join(",", ossProperties.getFileExt()));
            throw new RuntimeException(error);
        }
        try {
            return upLoadFile(folderName, fileName, suffix, file.getInputStream());
        } catch (Exception e) {
            log.error("minio upLoadFile Exception:{}", e);
            throw new RuntimeException("文件上传失败,请重新上传或联系管理员");
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param fullName 文件全名
     * @return {String}
     */
    public static String getFileExtension(String fullName) {
        Assert.notNull(fullName, "minio file fullName is null.");
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * 上传文件
     *
     * @param folderName 上传的文件夹名称
     * @param fileName   存储桶对象名称
     * @param suffix     文件后缀名
     * @param stream     文件流
     * @return BladeFile
     */
    @Override
    public OssFile upLoadFile(String folderName, String fileName, String suffix, InputStream stream) {
        try {
            return upLoadFile(ossProperties.getBucketName(), folderName, fileName, suffix, stream,
                    "application/octet" + "-stream");
        } catch (Exception e) {
            log.error("minio upLoadFile Exception:{}", e);
        }
        return null;
    }

    /**
     * @Description: 上传文件
     * @Param bucketName: 存储桶名称
     * @Param folderName: 上传的文件夹名称
     * @Param fileName: 上传文件名
     * @Param suffix: 文件后缀名
     * @Param stream: 文件流
     * @Param contentType: 文件类型
     * @Author: wmh
     * @Date: 2023/8/1 19:59
     */
    @SneakyThrows
    public OssFile upLoadFile(String bucketName, String folderName, String fileName, String suffix, InputStream stream,
                              String contentType) {
        if (!bucketExists(bucketName)) {
            log.info("minio bucketName is not creat");
            makeBucket(bucketName);
        }
        OssFile file = new OssFile();
        String originalName = fileName;
        String filePath = getFilePath(folderName, fileName, suffix);
        client.putObject(PutObjectArgs.builder().bucket(getBucketName(bucketName)).object(filePath)
                .stream(stream, stream.available(), -1).contentType(contentType).build());
        file.setOriginalName(originalName);
        file.setName(filePath);
        file.setDomain(getOssHost(bucketName));
        file.setFilePath(filePath);
        stream.close();
        log.info("minio upLoadFile success, filePath:{}", filePath);
        return file;
    }

    /**
     * 删除文件
     *
     * @param fileName 存储桶对象名称
     */
    @Override
    public boolean removeFile(String fileName) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(getBucketName(ossProperties.getBucketName())).object(fileName)
                            .build());
            log.info("minio removeFile success, fileName:{}", fileName);
            return true;
        } catch (Exception e) {
            log.error("minio removeFile fail, fileName:{}, Exception:{}", fileName, e);
        }
        return false;
    }

    /**
     * 批量删除文件
     *
     * @param fileNames 存储桶对象名称集合
     */
    @Override
    public boolean removeFiles(List<String> fileNames) {
        try {
            Stream<DeleteObject> stream = fileNames.stream().map(DeleteObject::new);
            client.removeObjects(RemoveObjectsArgs.builder().bucket(getBucketName(ossProperties.getBucketName()))
                    .objects(stream::iterator).build());
            log.info("minio removeFiles success, fileNames:{}", fileNames);
            return true;
        } catch (Exception e) {
            log.error("minio removeFiles fail, fileNames:{}, Exception:{}", fileNames, e);
        }
        return false;
    }

    /**
     * @Description: 下载文件
     * @Param response: 响应
     * @Param fileName: 文件名
     * @Param filePath: 文件路径
     * @return: void
     * @Author: wmh
     * @Date: 2023/8/2 14:08
     */
    @Override
    public void downloadFile(HttpServletResponse response, String fileName, String filePath) {
        GetObjectResponse is = null;
        try {
            GetObjectArgs getObjectArgs =
                    GetObjectArgs.builder().bucket(ossProperties.getBucketName()).object(filePath)
                            .build();
            is = client.getObject(getObjectArgs);
            // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding(ENCODING);
            // 设置文件头：最后一个参数是设置下载的文件名并编码为UTF-8
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, ENCODING));
            IoUtil.copy(is, response.getOutputStream());
            log.info("minio downloadFile success, filePath:{}", filePath);
        } catch (Exception e) {
            log.error("minio downloadFile Exception:{}", e);
        } finally {
            IoUtil.close(is);
        }
    }

    /***
     * @Description 获取获取图片地址
     * @return {@link java.lang.String }
     * @Author yaoHui
     * @Date 2024/10/31
     */
    @Override
    public String getImgWebSiteUrl() {
        return ossProperties.getImgWebSiteUrl();
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     * @param expires    过期时间
     * @return url
     */
    public String getPresignedObjectUrl(String bucketName, String fileName, Integer expires) {
        String link = "";
        try {
            link = client.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(getBucketName(bucketName))
                            .object(fileName).expiry(expires).build());
        } catch (Exception e) {
            log.error("minio getPresignedObjectUrl is fail, fileName:{}", fileName);
        }
        return link;
    }

    /**
     * 根据规则生成存储桶名称规则
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    private String getBucketName(String bucketName) {
        return bucketName;
    }

    /**
     * 根据规则生成文件路径
     *
     * @param folderName       上传的文件夹名称
     * @param originalFilename 原始文件名
     * @param suffix           文件后缀名
     * @return string 上传的文件夹名称/yyyyMMdd/原始文件名_时间戳.文件后缀名
     */
    private String getFilePath(String folderName, String originalFilename, String suffix) {
        return StrPool.SLASH + String.join(StrPool.SLASH, folderName, originalFilename)
                + StrPool.C_UNDERLINE + DateUtil.current() + StrPool.DOT + suffix;
    }

    /**
     * 获取域名
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    public String getOssHost(String bucketName) {
        return ossProperties.getEndpoint() + StrPool.SLASH + getBucketName(bucketName);
    }


}
