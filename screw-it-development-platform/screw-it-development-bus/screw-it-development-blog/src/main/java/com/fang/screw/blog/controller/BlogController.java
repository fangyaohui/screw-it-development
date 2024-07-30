package com.fang.screw.blog.controller;

import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @FileName BlogController
 * @Description
 * @Author yaoHui
 * @date 2024-07-30
 **/
@RestController
@RequestMapping("/blog")
@Slf4j
@AllArgsConstructor
public class BlogController {


    @PostMapping("/addBlogMDFile")
    public R<String> addBlogMDFile(@RequestParam("file") MultipartFile file) throws IOException {

        log.info(file.toString());

        String fileName = file.getOriginalFilename();
        InputStream stream = file.getInputStream();

        return R.ok("fasd");
    }

    @RequestMapping("/test")
    public R<String> test(){
        return R.ok("test");
    }
}