package com.fang.screw.blog.controller;

import com.fang.screw.communal.template.OssTemplate;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private OssTemplate ossTemplate;

    /**
    * @Description
    * @param file
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/7/30
    */
    @PostMapping("/addBlogMDFile")
    public R<String> addBlogMDFile(@RequestParam("file") MultipartFile file) throws IOException {

        log.info(file.toString());

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }

        log.info(contentBuilder.toString());

        return R.ok(contentBuilder.toString());
    }

    @RequestMapping("/test")
    public R<String> test(){
        return R.ok("test");
    }

    @GetMapping("/uploadImg")
    public R<String> uploadImg(@RequestParam("file") MultipartFile file){
        if(ossTemplate.bucketExists("screw-it-development-blog-buck")){
            log.info("screw-it-development-blog-buck存在");
        }
        log.info(String.valueOf(ossTemplate.upLoadFile("blogFoladerName","blogFileName",file)));
        return R.ok("test");
    }
}
