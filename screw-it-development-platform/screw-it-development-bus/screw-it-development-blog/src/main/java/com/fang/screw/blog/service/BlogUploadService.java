package com.fang.screw.blog.service;

import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.utils.R;
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

    R<OssFile> uploadImage (MultipartFile file) throws IOException;
}