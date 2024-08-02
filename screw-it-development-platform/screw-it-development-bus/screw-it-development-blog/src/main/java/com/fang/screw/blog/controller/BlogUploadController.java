package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.BlogUploadService;
import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public R<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        return blogUploadService.uploadBlogImage(file);

    }

    /**
    * @Description 获取minio服务器中的图片
    * @param imageName
    * @return {@link R< OssFile> }
    * @Author yaoHui
    * @Date 2024/8/1
    */
    @GetMapping("/getImage")
    public ResponseEntity<InputStreamResource> getImage(String imageName){
        return blogUploadService.getImage(imageName);
    }


}
