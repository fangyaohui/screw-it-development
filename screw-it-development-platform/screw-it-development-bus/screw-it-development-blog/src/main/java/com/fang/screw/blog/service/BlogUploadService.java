package com.fang.screw.blog.service;

import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.utils.R;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @FileName BlogUploadService
 * @Description 博客上传等业务逻辑处理模块
 * @Author yaoHui
 * @date 2024-07-31
 **/
public interface BlogUploadService {

    /**
     * @Description 上传MD文件博客
     * @param file
     * @return {@link R < Boolean> }
     * @Author yaoHui
     * @Date 2024/7/31
     */
    R<String> uploadMDFile(MultipartFile file) throws IOException;

    /**
     * @Description 上传博客图片
     * @param file
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/7/31
     */
    R<OssFile> uploadBlogImage(MultipartFile file) throws IOException;

    /**
     * @Description 获取minio服务器中的图片
     * @param imageName
     * @return {@link R< OssFile> }
     * @Author yaoHui
     * @Date 2024/8/1
     */
    ResponseEntity<InputStreamResource> getImage(String imageName);
}
