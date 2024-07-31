package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.BlogUploadService;
import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.template.OssTemplate;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @FileName BlogUploadController
 * @Description 博客上传Controller
 * @Author yaoHui
 * @date 2024-07-31
 **/
@RestController
@RequestMapping("/blog/upload")
@AllArgsConstructor
@Slf4j
public class BlogUploadController {

    private BlogUploadService blogUploadService;

    /**
    * @Description 上传MD文件博客
    * @param file
    * @return {@link R< Boolean> }
    * @Author yaoHui
    * @Date 2024/7/31
    */
    @PostMapping("/uploadMDFile")
    public R<String> uploadMDFile(@RequestParam("file") MultipartFile file) throws IOException {
        return blogUploadService.uploadMDFile(file);
    }

    /**
    * @Description 上传博客图片
    * @param file
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/7/31
    */
    @PostMapping("/uploadImage")
    public R<OssFile> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        return blogUploadService.uploadImage(file);

    }


}